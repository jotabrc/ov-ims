package io.github.jotabrc.ov_ims_inv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OvImsInvApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvImsInvApplication.class, args);
	}

}

