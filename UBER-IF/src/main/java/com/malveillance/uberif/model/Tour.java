package com.malveillance.uberif.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The class represents a delivery tour that consists of a sequence of deliveries.
 * It includes methods for adding deliveries, accessing the starting point, and generating a unique identifier.
 */
public class Tour {

    /**
     * The list of deliveries in the tour.
     */
    private List<Delivery> deliveries;

    /**
     * The unique identifier of the tour.
     */
    private int id;

    /**
     * Constructs a new Tour with a randomly generated unique identifier and an empty list of deliveries.
     */
    public Tour() {
        deliveries = new ArrayList<>();
        Random randomInt = new Random();
        id = randomInt.nextInt(Integer.MAX_VALUE);
    }

    /**
     * Constructs a new Tour with the specified unique identifier and an empty list of deliveries
     * @param id the unique identifier of the tour
     */
    public Tour(int id) {
        deliveries = new ArrayList<>();
        this.id = id;
    }

    /**
     * Constructs a new Tour with a randomly generated unique identifier and a starting delivery
     * @param startingDelivery the starting delivery of the tour
     */
    public Tour(Delivery startingDelivery) {
        deliveries = new ArrayList<>();
        deliveries.add(startingDelivery);
        Random randomInt = new Random();
        id = randomInt.nextInt(Integer.MAX_VALUE);
    }

    /**
     * Constructs a new Tour by copying another Tour
     * @param other the tour to be copied
     */
    public Tour(Tour other) {
        this.id = other.id;
        this.deliveries = new ArrayList<>();
        for (Delivery delivery : other.deliveries) {
            this.deliveries.add(new Delivery(delivery));
        }
    }

    /**
     * Gets the unique identifier of the tour
     * @return the unique identifier of the tour
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the tour
     * @param id the new unique identifier of the tour
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the list of deliveries in the tour
     * @return the list of deliveries in the tour
     */
    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    /**
     * Sets the list of deliveries in the tour
     * @param deliveries the new list of deliveries
     */
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * Adds a delivery to the tour
     * @param delivery the delivery to be added
     */
    public void addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
    }

    /**
     * Gets the starting point (first delivery) of the tour
     * @return the starting point of the tour
     */
    public Delivery getStartingPoint() {
        return this.deliveries.get(0);
    }

    /**
     * Returns a string representation of the tour
     * @return a string representation of the tour
     */
    @Override
    public String toString() {
        return "Tour{" +
                "deliveries=" + deliveries +
                '}';
    }

    /**
     * Checks if this tour is equal to another object based on its list of deliveries
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(deliveries, tour.deliveries);
    }

    /**
     * Computes the hash code of the tour based on its list of deliveries
     * @return the hash code of the tour
     */
    @Override
    public int hashCode() {
        return Objects.hash(deliveries);
    }
}
