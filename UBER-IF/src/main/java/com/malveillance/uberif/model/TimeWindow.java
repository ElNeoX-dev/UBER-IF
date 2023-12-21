package com.malveillance.uberif.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TimeWindow {

    public static final int defaultStartingHour = 8;
    private Date startingTime;

    private Date endingTime;

    public TimeWindow(Date startingTime, Date endingTime) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public TimeWindow(Date startingTime, int minutes) {
        this.startingTime = startingTime;
        Calendar cl = Calendar.getInstance();
        cl.setTime(startingTime);
        cl.add(Calendar.MINUTE, minutes);
        this.endingTime = cl.getTime();
    }

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

    public TimeWindow(int minutes) {
        this(defaultStartingHour,minutes);
    }

    public TimeWindow(TimeWindow other) {
        this.startingTime = (Date) other.startingTime.clone();
        this.endingTime = (Date) other.endingTime.clone();
    }
    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public Boolean isWithin(Date time) {
        return (this.startingTime.before(time) && this.endingTime.after(time));
    }

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
