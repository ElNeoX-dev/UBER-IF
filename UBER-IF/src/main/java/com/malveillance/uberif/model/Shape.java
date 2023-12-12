package com.malveillance.uberif.model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public abstract class Shape {

    public abstract void accept( ShapeVisitor v );
}
