package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.dto.ProductDtoUuidAndQuantity;
import io.github.jotabrc.ov_ims_inv.dto.UpdateType;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperServiceImpl implements MapperService {

    private final EntityCreatorMapper entityCreatorMapper;
    private final DtoMapper dtoMapper;

    @Override
    public <T, U, R> R transform(T t, U u, Class<R> type) {
        return switch (t) {
            case Inventory e -> type.isInstance(dtoMapper.toDto(e)) ?
                    type.cast(dtoMapper.toDto(e)) : ifUnsupportedThrow(t, type);
            case ProductDtoUuidAndQuantity e -> switch (u) {
                case UpdateType updateType -> type.isInstance(dtoMapper.toDto(e, updateType)) ?
                        type.cast(dtoMapper.toDto(e, updateType)) : ifUnsupportedThrow(u, type);
                case null -> type.isInstance(dtoMapper.toDto(e)) ?
                        type.cast(dtoMapper.toDto(e)) : ifUnsupportedThrow(e, type);
                default -> ifUnsupportedThrow(u, type);
            };
            case String string -> type.isInstance(entityCreatorMapper.toEntity(string)) ?
                    type.cast(entityCreatorMapper.toEntity(string)) : ifUnsupportedThrow(t, type);
            default -> ifUnsupportedThrow(t, type);
        };
    }

    private <T, R> R ifUnsupportedThrow(T t, Class<R> expected) {
        throw new IllegalArgumentException(
                "Transformation type not supported (%s), expected (%s)"
                        .formatted(t.getClass(), expected)
        );
    }
}
