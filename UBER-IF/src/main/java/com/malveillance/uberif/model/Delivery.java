package com.malveillance.uberif.model;

public class Delivery {
    private Intersection intersection;

    private TimeWindow timeWindow;

    private Courier courier;


    public Delivery(Intersection intersection, TimeWindow timeWindow, Courier courier) {
        this.intersection = intersection;
        this.timeWindow = timeWindow;
        this.courier = courier;
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

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "intersection=" + intersection +
                ", timeWindow=" + timeWindow +
                ", courier=" + courier +
                '}';
    }
}
