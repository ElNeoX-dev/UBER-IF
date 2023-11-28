package com.malveillance.uberif.model;

public class RoadSegment extends Shape{
    String destination;
    double length;
    String name;
    String origin;

    RoadSegment(String origin, String destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Segment: " + name + " [From: " + origin + " To: " + destination + ", Length: " + length + "]";
    }

}
