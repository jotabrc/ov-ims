package io.github.jotabrc.ov_ims_order.service;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.repository.DetailRepository;
import io.github.jotabrc.ov_ims_order.util.EntityCreatorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;

    private final EntityCreatorMapper entityCreatorMapper;

    @Transactional
    @Override
    public List<Detail> saveAll(final List<DetailDto> dtos) {
        List<Detail> details = entityCreatorMapper.toEntity(dtos);
        return detailRepository.saveAll(details);
    }
}
