package com.malveillance.uberif.model;

import javafx.scene.shape.Line;

public class RoadSegment extends Shape {
    private Intersection destination;
    private double length;
    private String name;
    private Intersection origin;

    protected Line road ;

    public RoadSegment(Intersection origin, Intersection destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    public Intersection getDestination() {
        return destination;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intersection getOrigin() {
        return origin;
    }

    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    public void accept( ShapeVisitor v ){
        v.visit(this);
    }

    @Override
    public String toString() {
        return "RoadSegment{" +
                "destination='" + destination + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }

}
