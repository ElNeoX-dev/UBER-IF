package com.malveillance.uberif.model;

import java.util.Calendar;
import java.util.Date;

public class TimeWindow {
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
}
