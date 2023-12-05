package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;

public class IntersectionClickHandler implements EventHandler<MouseEvent> {
    private final Intersection intersection;
    private Dot intersectionDot;

    private GraphicalView graphicalView;

    public IntersectionClickHandler(Dot intersectionDot, Intersection intersection, GraphicalView graphicalView) {
        this.intersection = intersection;
        this.intersectionDot = intersectionDot;
        this.graphicalView = graphicalView;
    }

    @Override
    public void handle(MouseEvent event) {
        //System.out.println("Mouse clicked over intersection (" + intersection.getId() + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");

        Courier courier = graphicalView.getSelectedCourier().getKey();
        List<Intersection> selectedListIntersections = graphicalView.getSelectedCourier().getValue();

        if (!courier.getName().isEmpty()) {
            if (selectedListIntersections.contains(intersection)) {
                selectedListIntersections.remove(intersection);
                //intersectionDot.setOwner(null);
                intersectionDot.setFill(Color.RED);
            } else {
                selectedListIntersections.add(intersection);
                //intersectionDot.setOwner(courier);
                intersectionDot.setFill(courier.getColor());
                intersectionDot.setRadius(graphicalView.height/220);
            }
        }
    }
}
