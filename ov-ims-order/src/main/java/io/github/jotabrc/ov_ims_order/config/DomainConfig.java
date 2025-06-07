package io.github.jotabrc.ov_ims_order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "domain.rules.return")
public class DomainConfig {

    private int maxDays = 30;
    private int minQuantity = 1;
}
