package io.github.jotabrc.ov_ims_product.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_product.config.RedisConfig;
import io.github.jotabrc.ov_ims_product.dto.PageFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class CacheAop {

    private final RedisConfig redisConfig;
    private final ObjectMapper objectMapper;

    @Around("@annotation(io.github.jotabrc.ov_ims_product.util.Cache)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();
        log.info("Cache lookup started for {}", className);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Cache annotation = method.getAnnotation(Cache.class);
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        StringJoiner keyJoin = new StringJoiner(":");

        Arrays.stream(joinPoint.getArgs())
                .forEach(arg -> {
                    switch (arg) {
                        case PageFilter p -> keyJoin
                                .add("uuid")
                                .add(p.getUuid())
                                .add("category")
                                .add(p.getCategory());
                        case Pageable p -> keyJoin
                                .add("pageSize")
                                .add(String.valueOf(p.getPageSize()))
                                .add("pageNumber")
                                .add(String.valueOf(p.getPageNumber()))
                                .add("offSet")
                                .add(String.valueOf(p.getOffset()))
                                .add("sort")
                                .add(
                                        p.getSort()
                                                .stream()
                                                .map(s -> s.getProperty() + ":" + s.getDirection())
                                                .collect(Collectors.joining("-", ":", ":"))
                                );
                        case null, default -> {
                        }
                    }
                });

        Object cache = redisConfig.redisTemplate().opsForValue().get(keyJoin.toString());

        if (cache != null) {
            log.info("Found cache for key {}", keyJoin);
        } else {
            log.info("Saving new cache with key {}", keyJoin);
            cache = joinPoint.proceed();
            redisConfig.redisTemplate().opsForValue().setIfAbsent(keyJoin.toString(), cache);
        }
        return cache;
    }

    private String serialize(Object object) {
        try {
            if (object instanceof Page<?> p) object = p.get().toList();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Serialization error: {}", e.getMessage());
            return "{}";
        }
    }

    private <R> R deserialize(Object object, Class<R> clazz) {
        return objectMapper.convertValue(object, clazz);
    }
}
