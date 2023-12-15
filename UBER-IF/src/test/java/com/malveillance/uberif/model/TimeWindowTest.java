package com.malveillance.uberif.model;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TimeWindowTest {

    @Test
    void constructors_ShouldCorrectlyInitializeFields() {
        // Test the constructor with specific starting and ending times
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY, 2);
        Date endTime = cal.getTime();
        TimeWindow tw = new TimeWindow(startTime, endTime);
        //Problèmes avec les assertEquals
        //assertEquals(startTime, tw.getStartingTime());
        //assertEquals(endTime, tw.getEndingTime());
        assertEquals(startTime.getTime(), tw.getStartingTime().getTime());
        assertEquals(endTime.getTime(), tw.getEndingTime().getTime());


        // Test the constructor with starting time and duration in minutes
        cal.setTime(startTime);
        int duration = 120; // 2 hours
        tw = new TimeWindow(startTime, duration);
        assertEquals(startTime, tw.getStartingTime());
        cal.add(Calendar.MINUTE, duration);
        assertEquals(cal.getTime(), tw.getEndingTime());

        // Test the constructor with hour and minutes for the day
        int hour = 8;
        int minutes = 30;
        tw = new TimeWindow(hour, minutes);
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date expectedStart = cal.getTime();
        cal.add(Calendar.MINUTE, minutes);
        Date expectedEnd = cal.getTime();
        assertEquals(expectedStart, tw.getStartingTime());
        assertEquals(expectedEnd, tw.getEndingTime());

        // Test the constructor with only minutes for the day
        tw = new TimeWindow(duration);
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, TimeWindow.defaultStartingHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        expectedStart = cal.getTime();
        cal.add(Calendar.MINUTE, duration);
        expectedEnd = cal.getTime();
        assertEquals(expectedStart, tw.getStartingTime());
        assertEquals(expectedEnd, tw.getEndingTime());
    }

    @Test
    void getAndSetStartingTime_ShouldReturnAndUpdateValue() {
        TimeWindow tw = new TimeWindow(new Date(), 30);
        Date newStart = new Date();
        tw.setStartingTime(newStart);
        assertEquals(newStart, tw.getStartingTime());
    }

    @Test
    void getAndSetEndingTime_ShouldReturnAndUpdateValue() {
        TimeWindow tw = new TimeWindow(new Date(), 30);
        Date newEnd = new Date();
        tw.setEndingTime(newEnd);
        assertEquals(newEnd, tw.getEndingTime());
    }

    @Test
    void isWithin_ShouldReturnTrueForTimeWithinWindow() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        int duration = 120; // 2 hours
        TimeWindow tw = new TimeWindow(startTime, duration);

        cal.add(Calendar.HOUR_OF_DAY, 1); // Add 1 hour, should still be within window
        assertTrue(tw.isWithin(cal.getTime()));
    }

    @Test
    void isWithin_ShouldReturnFalseForTimeOutsideWindow() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        int duration = 60; // 1 hour
        TimeWindow tw = new TimeWindow(startTime, duration);

        cal.add(Calendar.HOUR_OF_DAY, 2); // Add 2 hours, should be outside window
        assertFalse(tw.isWithin(cal.getTime()));
    }

    @Test
    void toString_ShouldReturnCorrectFormat() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        TimeWindow tw = new TimeWindow(startTime, 60);
        String expected = "TimeWindow{" +
                "startingTime=" + tw.getStartingTime() +
                ", endingTime=" + tw.getEndingTime() +
                '}';
        assertEquals(expected, tw.toString());
    }
}
