package com.shop.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;

public class NumberUtil {

    private static final Logger log = LoggerFactory.getLogger(NumberUtil.class);

    private NumberUtil() {}

    public static Long convertStringToLong(String value) {
        return StringUtils.isNotBlank(value) ? NumberUtils.parseNumber(value, Long.class) : null;
    }

    public static Integer convertStringToInteger(String value) {
        return StringUtils.isNotBlank(value) ? NumberUtils.parseNumber(value, Integer.class) : null;
    }

    public static boolean compareSmaller(Long first, Long second) {
        if (first == null || second == null) return false;
        return first < second;
    }
}
