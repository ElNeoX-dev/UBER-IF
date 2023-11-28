package com.malveillance.uberif.model;

public class Warehouse {
    String address;

    Warehouse(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Warehouse Address: " + address;
    }
}
