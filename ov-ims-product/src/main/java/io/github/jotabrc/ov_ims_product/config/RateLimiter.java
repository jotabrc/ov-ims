package io.github.jotabrc.ov_ims_product.config;

import io.github.jotabrc.ov_ims_product.controller.handler.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@AllArgsConstructor
@Slf4j
public class RateLimiter extends OncePerRequestFilter {

    private final RedisConfig redisConfig;
    private final RateLimiterConfig rateLimiterConfig;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String key = getKey(request);
            if (key != null) {
                doCache(response, key);
            }
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error("Rate limiting failed: {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }

    private String getKey(HttpServletRequest request) {
        String key = request.getHeader("X-Forwarded-For");
        key = key != null ? key
                .split(",")[0]
                .trim() : request.getRemoteAddr();

        if (key.isEmpty()) {
            return null;
        }
        return key;
    }

    private void doCache(HttpServletResponse response, String key) {
        Boolean firstAttempt = redisConfig
                .redisTemplate()
                .opsForValue()
                .setIfAbsent(key, 1, Duration.ofSeconds(rateLimiterConfig.getTTL()));
        Long tries = 1L;
        if (Boolean.FALSE.equals(firstAttempt)) {
            tries = redisConfig
                    .redisTemplate()
                    .opsForValue()
                    .increment(key, 1);
            redisConfig
                    .redisTemplate()
                    .expire(key, Duration.ofSeconds(rateLimiterConfig.getTTL()));
        }
        if (tries != null && tries >= rateLimiterConfig.getTRIES()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            throw new TooManyRequestsException("Rate limit exceeded");
        }
    }
}
