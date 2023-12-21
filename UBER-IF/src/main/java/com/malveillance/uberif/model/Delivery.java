package com.malveillance.uberif.model;

import java.util.Objects;

public class Delivery {
    private Intersection intersection;

    private TimeWindow timeWindow;

    public Delivery(Intersection intersection, TimeWindow timeWindow) {
        this.intersection = intersection;
        this.timeWindow = timeWindow;
    }

    // Copy constructor
    public Delivery(Delivery other) {
        this.intersection = new Intersection(other.intersection);
        this.timeWindow = new TimeWindow(other.timeWindow);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(intersection, delivery.intersection) && Objects.equals(timeWindow, delivery.timeWindow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intersection, timeWindow);
    }
}
