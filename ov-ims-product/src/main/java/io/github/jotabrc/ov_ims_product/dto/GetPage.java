package io.github.jotabrc.ov_ims_product.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class GetPage<T> implements Serializable {

    private List<T> content;
    private Boolean last;
    private long totalElements;
    private long totalPages;
    private Boolean first;
    private long size;
    private long number;
    private long numberOfElements;
    private Boolean empty;
}
