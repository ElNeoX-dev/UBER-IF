package com.malveillance.uberif.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tour {
    private List<Delivery> deliveries;

    private int id;

    public Tour() {
        deliveries = new ArrayList<>();
        Random randomInt = new Random();
        id = randomInt.nextInt(Integer.MAX_VALUE);
    }

    public Tour(Delivery startingDelivery) {
        deliveries = new ArrayList<>();
        deliveries.add(startingDelivery);
        Random randomInt = new Random();
        id = randomInt.nextInt(Integer.MAX_VALUE);
    }

    public int getId() {
        return id;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
    }

    public Delivery getStartingPoint() {
        return this.deliveries.get(0);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "deliveries=" + deliveries +
                '}';
    }
}