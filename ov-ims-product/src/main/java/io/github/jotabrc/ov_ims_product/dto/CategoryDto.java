package io.github.jotabrc.ov_ims_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jotabrc.ov_ims_product.validation.StringType;
import io.github.jotabrc.ov_ims_product.validation.ValidString;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CategoryDto implements Serializable {

    @ValidString(message = "Invalid UUID format", type = StringType.UUID, isRequired = false)
    private String uuid;

    @NotNull @ValidString(message = "Invalid Category name format", type = StringType.NAME)
    private final String name;

    @JsonCreator
    public CategoryDto(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name
    ) {
        this.uuid = uuid;
        this.name = name;
    }
}
