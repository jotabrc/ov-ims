package io.github.jotabrc.ov_ims_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync @EnableCaching
public class OvImsProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvImsProductApplication.class, args);
	}

}
