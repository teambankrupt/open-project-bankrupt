package com.example.common.misc;

import com.example.common.utils.DateUtil;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomDateFormat extends DateFormat {
    private static final List<? extends DateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat(DateUtil.SERVER_DATE_TIME_PATTERN),
            new SimpleDateFormat(DateUtil.DATE_PATTERN_BACKWARDS),
            new SimpleDateFormat(DateUtil.DATE_TIME_PATTERN_BACKWARDS)
    );

    @Override
    public StringBuffer format(final Date date, final StringBuffer toAppendTo, final FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("This custom date formatter can only be used to *parse* Dates.");
    }

    @Override
    public Date parse(final String source, final ParsePosition pos) {
        Date res;
        for (final DateFormat dateFormat : DATE_FORMATS) {
            if ((res = dateFormat.parse(source, pos)) != null) {
                return res;
            }
        }
        return null;
    }
}
