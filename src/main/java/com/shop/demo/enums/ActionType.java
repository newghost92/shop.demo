package com.shop.demo.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ActionType implements PersistableEnum<Integer> {

    LOGIN(1, "LOGIN"),
    LOGOUT(2, "LOGOUT"),
    CHANGE_PASSWORD(3, "CHANGE_PASSWORD"),
    RESET_PASSWORD(4, "RESET_PASSWORD");

    private static final Map<Integer, ActionType> mapper = Arrays.stream(ActionType.values())
        .collect(Collectors.toMap(PersistableEnum::getValue, Function.identity()));

    private final Integer value;
    private final String name;

    ActionType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ActionType get(Integer typeValue) {
        if (typeValue == null) {
            return null;
        }
        return Optional.ofNullable(mapper.get(typeValue)).orElseThrow(InvalidActionTypeException::new);
    }

    public static class Converter extends AbstractEnumConverter<ActionType, Integer> {

        public Converter() {
            super(ActionType.class);
        }
    }

    private static class InvalidActionTypeException extends IllegalArgumentException {
        public InvalidActionTypeException() {
            super("ACTION TYPE INVALID!");
        }
    }
}
