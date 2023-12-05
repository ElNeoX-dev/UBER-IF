package com.malveillance.uberif.model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Intersection extends Shape {
    private String id;
    private double latitude;
    private double longitude;

    protected Circle circle;

    public Intersection(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.circle = new Circle();
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(double centerX, double centerY, double radius, Paint fill) {
        this.circle = new Circle(centerX, centerY, radius, fill);;
    }

    public void setFill(Paint fill) {
        circle.setFill(fill);;
    }

    public void setRadius(double radius) {
        circle.setRadius(radius);;
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

    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public String toString() {
        return "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
    }

    public void accept( ShapeVisitor v ){
            v.visit(this);
    }
}
