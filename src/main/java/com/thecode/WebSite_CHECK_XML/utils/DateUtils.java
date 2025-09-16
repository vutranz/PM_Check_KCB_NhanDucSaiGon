package com.thecode.WebSite_CHECK_XML.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    // Chuyển chuỗi yyyyMMddHHmm -> LocalDateTime
    public static LocalDateTime parseDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, FORMATTER);
    }

    // Tính khoảng cách phút giữa 2 thời điểm
    public static long minutesBetween(String start, String end) {
        LocalDateTime t1 = parseDateTime(start);
        LocalDateTime t2 = parseDateTime(end);
        return ChronoUnit.MINUTES.between(t1, t2);
    }
}
