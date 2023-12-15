package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;

public class IntersectionHoverHandler implements EventHandler<MouseEvent> {
    // private final Intersection intersection;

    private GraphicalView graphicalView;

    private Label intersectionLabel;

    public IntersectionHoverHandler(GraphicalView graphicalView,
            /* Intersection intersection, */ Label intersectionLabel) {
        // this.intersection = intersection;
        this.intersectionLabel = intersectionLabel;
        this.graphicalView = graphicalView;
    }

    @Override
    public void handle(MouseEvent event) {
        // System.out.println("Mouse over intersection (" + intersection.getId() + ") at
        // (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");
        Intersection intersectionNearest = null;
        double distanceMin = Double.MAX_VALUE;

        for (Intersection intersection : graphicalView.getCityMap().getNodes().keySet()) {
            if (intersection.isOwned()) {
                intersection.setRadius((graphicalView.height / 150) * graphicalView.coef);
            } else {
                intersection.setRadius((graphicalView.height / 220) * graphicalView.coef);
            }

            double distance = Math.sqrt(Math.pow(intersection.getCircle().getCenterX() - event.getX(), 2)
                    + Math.pow(intersection.getCircle().getCenterY() - event.getY(), 2));
            if (distance < distanceMin) {
                intersectionNearest = intersection;
                distanceMin = distance;
            }
        }

        if (intersectionNearest != null && distanceMin < graphicalView.width / 24) {
            intersectionLabel.setText(/*"Intersection n°" + intersectionNearest.getId() + "\n" + */"Lat: "
                    + intersectionNearest.getLatitude() + "\nLong: " + intersectionNearest.getLongitude());
            intersectionNearest.setRadius((graphicalView.height / 125) * graphicalView.coef);
        }

    }

}
