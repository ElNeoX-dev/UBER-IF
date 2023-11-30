package com.malveillance.uberif.model;

public class Intersection extends Shape {
    private String id;
    private double latitude;
    private double longitude;

    public Intersection(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean equals (Intersection intersection) {return (this.getId().equals(intersection.getId()));}

    @Override
    public String toString() {
        return "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
    }

    public void accept( ShapeVisitor v ){
            v.visit(this);
    }
}
