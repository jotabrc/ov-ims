package io.github.jotabrc.ov_ims_inv.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration @ConfigurationProperties(prefix = "ov.ims.validation.pattern")
public class ValidationConfig {

    private String name = "[\\p{L}\\p{M}\\.\\s'-]{4,255}";
    private String string = "^$|^[A-Za-z0-9.,;!?§¶(){}\\[\\]€$R$\\s\\p{M}\\p{L}]+$";
    private String uuid = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";
}
