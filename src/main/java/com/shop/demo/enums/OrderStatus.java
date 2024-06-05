package com.shop.demo.enums;

import com.shop.demo.config.Constants;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OrderStatus implements PersistableEnum<Integer> {
    CREATED(0, "Created", "Đã được tạo"),
    PARTIALLY_PAID(1, "Partially paid", "Đã thanh toán một phần"),
    PAID(2, "Paid", "Đã thanh toán"),
    CANCELLED(3, "Canceled", "Đã hủy");
    private static final Map<Integer, OrderStatus> mapper = Arrays.stream(OrderStatus.values())
        .collect(Collectors.toMap(PersistableEnum::getValue, Function.identity()));

    private final Integer value;
    private final String enName;
    private final String viName;

    OrderStatus(
        Integer value,
        String enName,
        String viName
    ) {
        this.value = value;
        this.enName = enName;
        this.viName = viName;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName(String language) {
        return Constants.DEFAULT_LANGUAGE.equals(language) ? this.enName : this.viName;
    }

    public static OrderStatus get(Integer value) {
        if (value == null) return null;
        return Optional.ofNullable(mapper.get(value))
            .orElseThrow(InvalidPromotionStatusException::new);
    }

    public static class Converter extends AbstractEnumConverter<OrderStatus, Integer> {

        public Converter() {
            super(OrderStatus.class);
        }
    }

    private static class InvalidPromotionStatusException extends IllegalArgumentException {
        public InvalidPromotionStatusException() {
            super("ORDER STATUS INVALID!");
        }
    }
}
