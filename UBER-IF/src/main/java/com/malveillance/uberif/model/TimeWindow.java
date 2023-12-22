package com.malveillance.uberif.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * The class represents a time interval with a starting and ending time.
 * It provides constructors for creating time windows with different input formats and methods
 * for checking if a given time is within the specified time window.
 */
public class TimeWindow {

    /**
     * The default starting hour for a time window.
     */
    public static final int defaultStartingHour = 8;

    /**
     * The starting time of the time window.
     */
    private Date startingTime;

    /**
     * The ending time of the time window.
     */
    private Date endingTime;

    /**
     * Constructs a new TimeWindow with the specified starting and ending times
     * @param startingTime the starting time of the time window
     * @param endingTime   the ending time of the time window
     */
    public TimeWindow(Date startingTime, Date endingTime) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    /**
     * Constructs a new TimeWindow with the specified starting time and duration in minutes
     * @param startingTime the starting time of the time window
     * @param minutes      the duration of the time window in minutes
     */
    public TimeWindow(Date startingTime, int minutes) {
        this.startingTime = startingTime;
        Calendar cl = Calendar.getInstance();
        cl.setTime(startingTime);
        cl.add(Calendar.MINUTE, minutes);
        this.endingTime = cl.getTime();
    }

    /**
     * Constructs a new TimeWindow with the specified starting hour and duration in minutes
     * @param hour    the starting hour of the time window
     * @param minutes the duration of the time window in minutes
     */
    public TimeWindow(int hour, int minutes) {
        Date startingTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startingTime);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        this.startingTime = calendar.getTime();

        calendar.add(Calendar.MINUTE, minutes);
        this.endingTime = calendar.getTime();
    }

    /**
     * Constructs a new TimeWindow with the default starting hour and specified duration in minutes
     * @param minutes the duration of the time window in minutes
     */
    public TimeWindow(int minutes) {
        this(defaultStartingHour, minutes);
    }

    /**
     * Constructs a new TimeWindow by copying another TimeWindow
     * @param other the time window to be copied
     */
    public TimeWindow(TimeWindow other) {
        this.startingTime = (Date) other.startingTime.clone();
        this.endingTime = (Date) other.endingTime.clone();
    }

    /**
     * Gets the starting time of the time window
     * @return the starting time
     */
    public Date getStartingTime() {
        return startingTime;
    }

    /**
     * Sets the starting time of the time window
     * @param startingTime the new starting time
     */
    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    /**
     * Gets the ending time of the time window
     * @return the ending time
     */
    public Date getEndingTime() {
        return endingTime;
    }

    /**
     * Sets the ending time of the time window
     * @param endingTime the new ending time
     */
    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    /**
     * Checks if a given time is within the specified time window
     * @param time the time to check
     * @return true if the time is within the time window, false otherwise
     */
    public Boolean isWithin(Date time) {
        return (this.startingTime.before(time) && this.endingTime.after(time));
    }

    /**
     * Returns a string representation of the time window
     * @return a string representation of the time window
     */
    @Override
    public String toString() {
        return "TimeWindow{" +
                "startingTime=" + startingTime +
                ", endingTime=" + endingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeWindow that = (TimeWindow) o;
        return Objects.equals(startingTime, that.startingTime) && Objects.equals(endingTime, that.endingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingTime, endingTime);
    }
}
