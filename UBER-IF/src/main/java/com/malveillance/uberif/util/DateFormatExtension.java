package com.malveillance.uberif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatExtension {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    private static String getTextFromDate(Date date) {
        return sdf.format(date);
    }

    private static Date getDateFromText(String dateText) throws ParseException {
        return sdf.parse(dateText);
    }
}