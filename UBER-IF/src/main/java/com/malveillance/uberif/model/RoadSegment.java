package com.malveillance.uberif.model;

import javafx.scene.shape.Line;

public class RoadSegment extends Shape {
    private Intersection destination;
    private double length;
    private String name;
    private Intersection origin;

    //protected Line road ;

    public RoadSegment(Intersection origin, Intersection destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    public RoadSegment(RoadSegment other) {
        this.origin = new Intersection(other.origin);
        this.destination = new Intersection(other.destination);

        this.length = other.length;
        this.name = other.name;
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

    public String getTurnDirection(RoadSegment previousSegment) {
        // Calculate the angle between the previous road segment and the current road segment
        double angle = calculateAngle(previousSegment);

        // Determine the turn direction based on the orientation of the turn
        if (angle < 10 && angle > -10) {
            return "zero";
        }
        if (angle <= 180) {
            return "left";
        } else {
            return "right";
        }
    }

    private double calculateAngle(RoadSegment previousSegment) {
        // Calculate the vectors representing the directions of the two road segments
        // Point 1 : origin prev
        // Point 2 : origin this
        // Point 3 : dest this
        // Calculate the vectors
        double vector1X = this.getOrigin().getLongitude() - previousSegment.getOrigin().getLongitude();
        double vector1Y = this.getOrigin().getLatitude() - previousSegment.getOrigin().getLatitude();
        double vector2X = this.getDestination().getLongitude() - this.getOrigin().getLongitude();
        double vector2Y = this.getDestination().getLatitude() - this.getOrigin().getLatitude();

        // Calculate the cross product of the two vectors
        double crossProduct = vector1X * vector2Y - vector1Y * vector2X;

        // Calculate the dot product of the two vectors
        double dotProduct = vector1X * vector2X + vector1Y * vector2Y;

        // Calculate the magnitudes of the vectors
        double magnitude1 = Math.sqrt(vector1X * vector1X + vector1Y * vector1Y);
        double magnitude2 = Math.sqrt(vector2X * vector2X + vector2Y * vector2Y);

        // Calculate the angle in radians
        double angleRad = Math.atan2(crossProduct, dotProduct);

        // Ensure the angle is within the range [0, 360]
        double angleDeg = Math.toDegrees(angleRad);
        angleDeg = (angleDeg + 360) % 360;

        return angleDeg;
    }

}
