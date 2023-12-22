package com.malveillance.uberif.model;

/**
 * The class is an abstract visitor for various shapes in the model.
 * Subclasses can implement specific behaviors for each type of shape by overriding the visit methods.
 */
public abstract class ShapeVisitor {

    /**
     * Visits a warehouse shape
     * @param warehouse the warehouse shape to visit
     */
    public abstract void visit(Warehouse warehouse);

    /**
     * Visits an intersection shape
     * @param intersection the intersection shape to visit
     */
    public abstract void visit(Intersection intersection);

    /**
     * Visits a road segment shape
     * @param segment the road segment shape to visit
     */
    public abstract void visit(RoadSegment segment);
}
