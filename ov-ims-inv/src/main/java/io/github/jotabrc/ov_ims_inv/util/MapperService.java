package io.github.jotabrc.ov_ims_inv.util;

public interface MapperService {

    <T, U, R> R transform(T t, U u, Class<R> type);
}
