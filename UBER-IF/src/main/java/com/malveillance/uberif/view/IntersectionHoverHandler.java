package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;

/**
 * The class represents a listener for the hover event on an intersection.
 */
public class IntersectionHoverHandler implements EventHandler<MouseEvent> {

    /**
     * The graphical view.
     */
    private GraphicalView graphicalView;

    /**
     * The intersection label.
     */
    private Label intersectionLabel;

    /**
     * Constructs a new IntersectionHoverHandler with the specified graphical view
     * and intersection label.
     *
     * @param graphicalView   the graphical view
     * @param intersectionLabel the intersection label
     */
    public IntersectionHoverHandler(GraphicalView graphicalView,
             Label intersectionLabel) {
        this.intersectionLabel = intersectionLabel;
        this.graphicalView = graphicalView;
    }

    /**
     * Handles the hover event on an intersection.
     *
     * @param event the hover event
     */
    @Override
    public void handle(MouseEvent event) {
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
            intersectionLabel.setText( /*"Intersection n°" + intersectionNearest.getId() + "\n*/"Lat: "
                    + intersectionNearest.getLatitude() + "\nLong: " + intersectionNearest.getLongitude());
            intersectionNearest.setRadius((graphicalView.height / 125) * graphicalView.coef);
        }

    }

}
