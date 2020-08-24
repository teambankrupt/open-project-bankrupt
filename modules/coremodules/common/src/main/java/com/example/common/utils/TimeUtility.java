package com.example.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TimeUtility {

    private TimeUtility() {
    }

    public static String readableInstant(Instant instant) {
        if (instant == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.US)
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

}
