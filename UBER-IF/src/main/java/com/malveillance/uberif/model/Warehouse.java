package com.malveillance.uberif.model;

public class Warehouse {
    public String address;

    public Warehouse(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Warehouse Address: " + address;
    }
}
