package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;

public class IntersectionHoverHandler implements EventHandler<MouseEvent> {
    //private final Intersection intersection;

    private GraphicalView graphicalView;

    private Label intersectionLabel;

    public IntersectionHoverHandler(GraphicalView graphicalView,/* Intersection intersection,*/ Label intersectionLabel) {
        //this.intersection = intersection;
        this.intersectionLabel = intersectionLabel;
        this.graphicalView = graphicalView;
    }

    @Override
    public void handle(MouseEvent event) {
        //System.out.println("Mouse over intersection (" + intersection.getId() + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");
        Intersection intersectionNearest = null;
        double distanceMin = Double.MAX_VALUE;
        for (Intersection intersection : graphicalView.getCityMap().getNodes().keySet()) {
            double distance = Math.sqrt(Math.pow(intersection.getCircle().getCenterX() - event.getX(), 2) + Math.pow(intersection.getCircle().getCenterY() - event.getY(), 2));
            if (distance < distanceMin) {
                intersectionNearest = intersection;
                distanceMin = distance;
            }

        }

        intersectionLabel.setText("Intersection n°" + intersectionNearest.getId() + "\nLat: " + intersectionNearest.getLatitude() + "\nLong: " + intersectionNearest.getLongitude());

        if (distanceMin < graphicalView.width / 24) {
            intersectionNearest.setRadius((graphicalView.height / 125)*graphicalView.coef);
        } else {
            if (intersectionNearest.isOwned()) {
                intersectionNearest.setRadius((graphicalView.height / 150)*graphicalView.coef);
            } else {
                intersectionNearest.setRadius((graphicalView.height / 220)*graphicalView.coef);
            }
        }

    }

}
