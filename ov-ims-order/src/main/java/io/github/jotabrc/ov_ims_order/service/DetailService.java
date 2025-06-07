package io.github.jotabrc.ov_ims_order.service;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.model.Detail;

import java.util.List;

public interface DetailService {

    List<Detail> saveAll(List<DetailDto> dtos);
}
