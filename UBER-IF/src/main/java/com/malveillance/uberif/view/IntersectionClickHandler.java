package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.List;

public class IntersectionClickHandler implements EventHandler<MouseEvent> {
    private final Intersection intersection;
    private Circle intersectionDot;

    private List<Intersection> selectedListIntersections;

    public IntersectionClickHandler(Circle intersectionDot, Intersection intersection, List<Intersection> selectedListIntersections) {
        this.intersection = intersection;
        this.intersectionDot = intersectionDot;
        this.selectedListIntersections = selectedListIntersections;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Mouse clicked over intersection (" + intersection.getId() + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");

        if (selectedListIntersections.contains(intersection)) {
            selectedListIntersections.remove(intersection);
            intersectionDot.setFill(Color.RED);
            intersectionDot.setRadius(3);
        } else {
            selectedListIntersections.add(intersection);
            intersectionDot.setFill(Color.BLUE);
            intersectionDot.setRadius(5);
        }
    }
}
