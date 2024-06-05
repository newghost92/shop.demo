package com.shop.demo.enums;

import com.shop.demo.config.Constants;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Status implements PersistableEnum<Integer> {

    INACTIVATE(0, "Inactive", "Không kích hoạt"),
    ACTIVATE(1, "Active", "Kích hoạt");

    private final Integer value;
    private final String enName;
    private final String viName;

    private static final Map<Integer, Status> mapper = Arrays.stream(Status.values())
        .collect(Collectors.toMap(PersistableEnum::getValue, Function.identity()));

    Status(Integer value, String enName, String viName) {
        this.value = value;
        this.enName = enName;
        this.viName = viName;
    }

    public static Status get(Integer value) {
        if (value == null) return null;
        return Optional.ofNullable(mapper.get(value))
            .orElseThrow(InvalidStatusException::new);
    }

    public String getNameByLang(String lang) {
        return Constants.DEFAULT_LANGUAGE.equals(lang) ? this.enName : this.viName;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static class Converter extends AbstractEnumConverter<Status, Integer> {
        public Converter() {
            super(Status.class);
        }
    }

    private static class InvalidStatusException extends IllegalArgumentException {
        public InvalidStatusException() {
            super("STATUS INVALID!");
        }
    }
}
