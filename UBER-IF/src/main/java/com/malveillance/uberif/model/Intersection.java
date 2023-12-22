package com.malveillance.uberif.model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * The class represents a geographical point on a map with a unique identifier,
 * latitude, and longitude. It extends the Shape class and includes additional properties
 * such as a graphical representation using a JavaFX
 */
public class Intersection extends Shape {

    /**
     * The unique identifier of the intersection.
     */
    private String id;

    /**
     * The latitude of the intersection.
     */
    private double latitude;

    /**
     * The longitude of the intersection.
     */
    private double longitude;

    /**
     * The graphical representation of the intersection using a JavaFX {@code Circle}.
     */
    protected Circle circle;

    /**
     * A flag indicating whether the intersection is owned or not.
     */
    protected boolean isOwned = false;

    /**
     * Constructs a new Intersection with the specified id, latitude, and longitude
     * @param id        the unique identifier of the intersection
     * @param latitude  the latitude of the intersection
     * @param longitude the longitude of the intersection
     */
    public Intersection(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.circle = new Circle();
    }

    /**
     * Constructs a new Intersection by copying another Intersection
     * @param other the intersection to be copied
     */
    public Intersection(Intersection other) {
        this.id = other.id;
        this.latitude = other.latitude;
        this.longitude = other.longitude;
        this.isOwned = other.isOwned;

        if (other.circle != null) {
            this.circle = new Circle(other.circle.getCenterX(), other.circle.getCenterY(),
                    other.circle.getRadius(), other.circle.getFill());
        } else {
            this.circle = null;
        }
    }

    /**
     * Gets the graphical representation of the intersection
     * @return the circle representing the intersection
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Sets the properties of the graphical representation of the intersection
     * @param centerX the X-coordinate of the center
     * @param centerY the Y-coordinate of the center
     * @param radius  the radius of the circle
     * @param fill    the fill color of the circle
     */
    public void setCircle(double centerX, double centerY, double radius, Paint fill) {
        this.circle = new Circle(centerX, centerY, radius, fill);
    }

    /**
     * Sets whether the intersection is owned or not
     * @param isOwned the flag indicating ownership
     */
    public void setIsOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }

    /**
     * Checks whether the intersection is owned
     * @return true if the intersection is owned, false otherwise
     */
    public boolean isOwned() {
        return isOwned;
    }

    /**
     * Sets the fill color of the circle representing the intersection
     * @param fill the fill color
     */
    public void setFill(Paint fill) {
        circle.setFill(fill);
    }

    /**
     * Sets the radius of the circle representing the intersection
     * @param radius the radius of the circle
     */
    public void setRadius(double radius) {
        circle.setRadius(radius);
    }

    /**
     * Gets the unique identifier of the intersection
     * @return the id of the intersection
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the intersection
     * @param id the new id for the intersection
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the latitude of the intersection
     * @return the latitude of the intersection
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the intersection
     * @param latitude the new latitude for the intersection
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the intersection
     * @return the longitude of the intersection
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the intersection
     * @param longitude the new longitude for the intersection
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Compares this intersection to another object for equality
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Intersection that = (Intersection) obj;
        return id != null && id.equals(that.id);
    }

    /**
     * Computes the hash code of the intersection based on its id
     * @return the hash code of the intersection
     */
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    /**
     * Returns a string representation of the intersection
     * @return a string representation of the intersection
     */
    @Override
    public String toString() {
        return "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
    }

    /**
     * Accepts a visitor for visiting this intersection as a shape
     * @param v the shape visitor
     */
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }
}