package io.github.jotabrc.ov_ims_order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration @ConfigurationProperties(prefix = "rate.limiter")
public class RateLimiterConfig {

    private Boolean ENFORCE = true;
    private Long TRIES = 50L;
    private Long TTL = 2L;
}
