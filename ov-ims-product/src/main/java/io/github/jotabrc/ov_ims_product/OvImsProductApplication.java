package io.github.jotabrc.ov_ims_product;

import io.github.jotabrc.ov_ims_product.config.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DomainConfig.class)
public class OvImsProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvImsProductApplication.class, args);
	}

}
