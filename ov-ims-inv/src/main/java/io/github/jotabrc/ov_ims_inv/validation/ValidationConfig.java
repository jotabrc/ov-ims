package io.github.jotabrc.ov_ims_inv.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration @ConfigurationProperties(prefix = "ov.ims.validation.pattern")
public class ValidationConfig {

    private String name;
    private String string;
    private String uuid;
}
