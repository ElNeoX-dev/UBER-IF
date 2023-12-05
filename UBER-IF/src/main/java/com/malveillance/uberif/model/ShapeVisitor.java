package com.malveillance.uberif.model;

public abstract class ShapeVisitor {
    public abstract void visit( Warehouse warehouse );
    public abstract void visit( Intersection intersection );
    public abstract void visit( RoadSegment segment );
}
