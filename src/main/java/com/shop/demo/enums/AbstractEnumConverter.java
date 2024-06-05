package com.shop.demo.enums;

import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AbstractEnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    public AbstractEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        T[] enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (e.getValue().equals(dbData)) {
                return e;
            }
        }
        throw new UnsupportedOperationException();
    }
}

