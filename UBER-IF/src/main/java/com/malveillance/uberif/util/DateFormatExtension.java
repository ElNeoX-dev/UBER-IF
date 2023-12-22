package com.malveillance.uberif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class provides methods for converting between Date and String.
 */
public class DateFormatExtension {
    /**
     * The date format used for converting between Date and String.
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    /**
     * Returns the String representation of a Date.
     * @param date the Date to be converted
     * @return the String representation of the Date
     */
    public static String getTextFromDate(Date date) {
        return sdf.format(date);
    }

    /**
     * Returns the Date representation of a String.
     * @param dateText the String to be converted
     * @return the Date representation of the String
     * @throws ParseException if the String cannot be parsed
     */
    public static Date getDateFromText(String dateText) throws ParseException {
        return sdf.parse(dateText);
    }
}