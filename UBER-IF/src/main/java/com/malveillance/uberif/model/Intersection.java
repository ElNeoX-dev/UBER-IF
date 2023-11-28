package com.malveillance.uberif.model;

public class Intersection extends Shape {
    String id;
    double latitude;
    double longitude;

    Intersection(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
    }

}
