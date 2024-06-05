package com.shop.demo.utils;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    public static final String DEFAULT_ZONE = "Asia/Ho_Chi_Minh";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_WITHOUT_CHARACTERS = "yyyyMMdd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String VI_DATE_FORMAT_WITHOUT_CHARACTERS = "ddMMyyyy";
    public static final String TIME_FORMAT_WITHOUT_CHARACTERS = "HHmmss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_WITHOUT_CHARACTERS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_WITH_SLASH = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT_WITH_SLASH = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_HOUR_MINUTE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DATE_TIME_FORMAT_FOR_CLIENT = "HH:mm:ss dd/MM/yyyy";
    public static final String HOUR_TIME_FORMAT = "HH:mm";
    public static final String HOUR_MINUTE_SECOND_FORMAT = "HH:mm:ss";

    public static final String HOUR_DATE_FORMAT = "HH:mm - dd/MM/yyyy";

    public static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final DateTimeFormatter formatterDateTimeWithoutCharacters = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_WITHOUT_CHARACTERS);
    public static final DateTimeFormatter formatterDateWithoutCharacters = DateTimeFormatter.ofPattern(DATE_FORMAT_WITHOUT_CHARACTERS);
    public static final DateTimeFormatter formatterTimeWithoutCharacters = DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_CHARACTERS);
    public static final DateTimeFormatter formatterSlashDate = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_SLASH);
    public static final DateTimeFormatter formatterSlashDateTime = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_WITH_SLASH);
    public static final DateTimeFormatter formatterClientDateHourMinute = DateTimeFormatter.ofPattern(DATE_HOUR_MINUTE_FORMAT);
    public static final DateTimeFormatter formatterClientDateTime = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_FOR_CLIENT);
    public static final DateTimeFormatter formatterVIDate = DateTimeFormatter.ofPattern(VI_DATE_FORMAT_WITHOUT_CHARACTERS);
    public static final DateTimeFormatter formatterHourTime = DateTimeFormatter.ofPattern(HOUR_TIME_FORMAT);
    public static final DateTimeFormatter formatterHourMinuteSecond = DateTimeFormatter.ofPattern(HOUR_MINUTE_SECOND_FORMAT);
    public static final DateTimeFormatter formatHourDate = DateTimeFormatter.ofPattern(HOUR_DATE_FORMAT);
    public static final DateTimeFormatter formatHourMinuteSecond = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static LocalDate convertStringToLocalDate(String localDateStr, DateTimeFormatter formatter) {
        if (localDateStr == null) return null;
        return LocalDate.parse(localDateStr, formatter);
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDatetime, ZoneId zoneId) {
        Instant instantDate = localDatetime.atZone(zoneId).toInstant();
        return Date.from(instantDate);
    }
}
