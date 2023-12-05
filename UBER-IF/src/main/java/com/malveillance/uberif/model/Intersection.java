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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Intersection that = (Intersection) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public String toString() {
        return "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
    }

    public void accept(ShapeVisitor v) {
        v.visit(this);
    }
}
