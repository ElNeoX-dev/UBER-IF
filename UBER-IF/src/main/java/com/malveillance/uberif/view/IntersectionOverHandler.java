package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import javafx.scene.control.Label;

public class IntersectionOverHandler implements EventHandler<MouseEvent> {
    private final Intersection intersection;

    private Label intersectionLabel;

    public IntersectionOverHandler(Intersection intersection, Label intersectionLabel) {
        this.intersection = intersection;
        this.intersectionLabel = intersectionLabel;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Mouse over intersection (" + intersection.getId() + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");
        intersectionLabel.setText("Informations : \nIntersection n°" + intersection.getId() + "\nLat: " + intersection.getLatitude() + "\nLong: " + intersection.getLongitude());
    }

}
