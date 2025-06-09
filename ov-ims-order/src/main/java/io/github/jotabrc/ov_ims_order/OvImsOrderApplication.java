package io.github.jotabrc.ov_ims_order;

import io.github.jotabrc.ov_ims_order.config.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync @EnableCaching
@EnableConfigurationProperties(DomainConfig.class)
public class OvImsOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvImsOrderApplication.class, args);
	}

}
