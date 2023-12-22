package com.malveillance.uberif.model;

import java.util.Objects;

/**
 * The class represents a delivery with information about the delivery location
 * and the time window within which the delivery is expected.
 */
public class Delivery {

    /**
     * The intersection where the delivery is to be made.
     */
    private Intersection intersection;

    /**
     * The time window within which the delivery is expected.
     */
    private TimeWindow timeWindow;

    /**
     * Constructs a new Delivery with the specified intersection and time window
     * @param intersection the intersection where the delivery is to be made
     * @param timeWindow   the time window for the delivery
     */
    public Delivery(Intersection intersection, TimeWindow timeWindow) {
        this.intersection = intersection;
        this.timeWindow = timeWindow;
    }

    /**
     * Constructs a new Delivery by copying another Delivery
     * @param other the delivery to be copied
     */
    public Delivery(Delivery other) {
        this.intersection = new Intersection(other.intersection);
        this.timeWindow = new TimeWindow(other.timeWindow);
    }

    /**
     * Gets the intersection where the delivery is to be made
     * @return the intersection of the delivery
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Sets the intersection where the delivery is to be made
     * @param intersection the new intersection for the delivery
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Gets the time window for the delivery
     * @return the time window for the delivery
     */
    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    /**
     * Sets the time window for the delivery
     * @param timeWindow the new time window for the delivery
     */
    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    /**
     * Returns a string representation of the delivery
     * @return a string representation of the delivery
     */
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
