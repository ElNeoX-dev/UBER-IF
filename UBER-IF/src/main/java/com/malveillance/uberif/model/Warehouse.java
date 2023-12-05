package com.malveillance.uberif.model;

public class Warehouse extends Shape {
    public Intersection intersection;

    public Warehouse(Intersection intersection) {
        this.intersection = intersection;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public void accept(ShapeVisitor v ){
        v.visit(this);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "intersection=" + intersection +
                '}';
    }
}
