package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Dot extends Circle {
    private Courier owner ;
    private Intersection intersection ;
    public Dot(/*Courier owner, */Intersection intersection, double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
        //this.owner = owner ;
        this.intersection = intersection ;

    }

    public boolean isFree() {
        return (owner == null) ;
    }
/*
    public Courier getOwner() {
        return owner;
    }

    public void setOwner(Courier owner) {
        this.owner = owner;
    }
*/
    public Intersection getIntersection() {
        return intersection;
    }
}
