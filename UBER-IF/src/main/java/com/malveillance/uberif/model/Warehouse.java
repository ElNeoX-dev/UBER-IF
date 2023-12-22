package com.malveillance.uberif.model;

/**
 * The class represents a warehouse as a shape with a specific intersection.
 * It extends the Shape class and includes methods for getting and setting the intersection,
 * as well as a copy constructor for creating a warehouse by copying another warehouse.
 */
public class Warehouse extends Shape {

    /**
     * The intersection associated with the warehouse.
     */
    public Intersection intersection;

    /**
     * Constructs a new Warehouse with the specified intersection
     * @param intersection the intersection associated with the warehouse
     */
    public Warehouse(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Copy constructor for creating a new Warehouse by copying another Warehouse
     * @param other the warehouse to be copied
     */
    public Warehouse(Warehouse other) {
        this.intersection = new Intersection(other.intersection);
    }

    /**
     * Gets the intersection associated with the warehouse
     * @return the intersection associated with the warehouse
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Sets the intersection associated with the warehouse
     * @param intersection the new intersection associated with the warehouse
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Accepts a visitor for visiting this warehouse as a shape
     * @param v the shape visitor
     */
    public void accept(ShapeVisitor v) {
        v.visit(this);
    }

    /**
     * Returns a string representation of the warehouse
     * @return a string representation of the warehouse
     */
    @Override
    public String toString() {
        return "Warehouse{" +
                "intersection=" + intersection +
                '}';
    }
}
