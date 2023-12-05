package com.malveillance.uberif.model;

public class Delivery {
    private Intersection intersection;

    private TimeWindow timeWindow;

    public Delivery(Intersection intersection, TimeWindow timeWindow) {
        this.intersection = intersection;
        this.timeWindow = timeWindow;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "intersection=" + intersection +
                ", timeWindow=" + timeWindow +
                '}';
    }
}
