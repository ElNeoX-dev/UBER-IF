package com.malveillance.uberif.model;

import javafx.scene.shape.Line;

/**
 * The class represents a segment of a road connecting two intersections.
 * It extends the Shape class and includes properties such as the origin, destination,
 * length, and name of the road segment
 */
public class RoadSegment extends Shape {

    /**
     * The intersection representing the destination of the road segment.
     */
    private Intersection destination;

    /**
     * The length of the road segment.
     */
    private double length;

    /**
     * The name of the road segment.
     */
    private String name;

    /**
     * The intersection representing the origin of the road segment.
     */
    private Intersection origin;

    /**
     * Constructs a new RoadSegment with the specified origin, destination, length, and name
     * @param origin      the origin intersection of the road segment
     * @param destination the destination intersection of the road segment
     * @param length      the length of the road segment
     * @param name        the name of the road segment
     */
    public RoadSegment(Intersection origin, Intersection destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    /**
     * Constructs a new RoadSegment by copying another RoadSegment
     * @param other the road segment to be copied
     */
    public RoadSegment(RoadSegment other) {
        this.origin = new Intersection(other.origin);
        this.destination = new Intersection(other.destination);
        this.length = other.length;
        this.name = other.name;
    }

    /**
     * Gets the destination intersection of the road segment
     * @return the destination intersection
     */
    public Intersection getDestination() {
        return destination;
    }

    /**
     * Sets the destination intersection of the road segment
     * @param destination the new destination intersection
     */
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    /**
     * Gets the length of the road segment
     * @return the length of the road segment
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the road segment
     * @param length the new length of the road segment
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Gets the name of the road segment
     * @return the name of the road segment
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the road segment
     * @param name the new name of the road segment
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the origin intersection of the road segment
     * @return the origin intersection
     */
    public Intersection getOrigin() {
        return origin;
    }

    /**
     * Sets the origin intersection of the road segment
     * @param origin the new origin intersection
     */
    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    /**
     * Accepts a visitor for visiting this road segment as a shape
     * @param v the shape visitor
     */
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }

    /**
     * Gets a string representation of the road segment
     * @return a string representation of the road segment
     */
    @Override
    public String toString() {
        return "RoadSegment{" +
                "destination='" + destination + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }

    /**
     * Determines the turn direction based on the angle between the previous road segment and the current road segment
     * @param previousSegment the previous road segment
     * @return a string indicating the turn direction ("zero", "left", or "right")
     */
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

    /**
     * Calculates the angle between the previous road segment and the current road segment
     * @param previousSegment the previous road segment
     * @return the angle in degrees
     */
    private double calculateAngle(RoadSegment previousSegment) {
        // Calculate the vectors representing the directions of the two road segments
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
