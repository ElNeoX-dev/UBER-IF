package com.malveillance.uberif.model;

public abstract class Shape {
    public abstract void accept( ShapeVisitor v );
}
