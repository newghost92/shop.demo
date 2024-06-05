package com.shop.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class BigDecimalUtil {

    public static final Integer SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public static BigDecimal setScale(BigDecimal number) {
        if (Objects.isNull(number)) {
            return null;
        }
        return number.setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal roundUp(BigDecimal number) {
        if (Objects.isNull(number)) {
            return BigDecimal.ZERO.setScale(0, RoundingMode.HALF_UP);
        }
        return number.setScale(0, RoundingMode.HALF_UP);
    }
}
