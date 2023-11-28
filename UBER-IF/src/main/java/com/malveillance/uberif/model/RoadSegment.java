package com.malveillance.uberif.model;

public class RoadSegment extends Shape{
    public String destination;
    public double length;
    public String name;
    public String origin;

    public RoadSegment(String origin, String destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Segment: " + name + " [From: " + origin + " To: " + destination + ", Length: " + length + "]";
    }

}
