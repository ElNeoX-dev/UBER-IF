package com.malveillance.uberif.model;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    private List<Delivery> deliveries;

    public Tour() {
        deliveries = new ArrayList<>();
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