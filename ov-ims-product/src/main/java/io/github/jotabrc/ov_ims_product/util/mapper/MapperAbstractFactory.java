package io.github.jotabrc.ov_ims_product.util.mapper;

@FunctionalInterface
public interface MapperAbstractFactory<T, R> {

    R mapFrom(T t);
}
