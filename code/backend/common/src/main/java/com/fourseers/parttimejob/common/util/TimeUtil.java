package com.fourseers.parttimejob.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public static final int DEFAULT_RADIUS_MINUTE = 15;

    public static boolean withIn(LocalDateTime dest, int radiusMinute) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(dest.minusMinutes(radiusMinute)) &&
                now.isBefore(dest.plusMinutes(radiusMinute));
    }

    public static boolean withIn(LocalDateTime dest) {
        return withIn(dest, DEFAULT_RADIUS_MINUTE);
    }

    public static boolean withIn(LocalTime dest, int radiusMinute) {
        return withIn(LocalDate.now().atTime(dest), radiusMinute);
    }

    public static boolean withIn(LocalTime dest) {
        return withIn(dest, DEFAULT_RADIUS_MINUTE);
    }

}
