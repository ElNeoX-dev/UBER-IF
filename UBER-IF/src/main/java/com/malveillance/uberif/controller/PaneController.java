package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.model.service.PaneService;

import java.util.Set;

/**
 * The class represents a controller for the pane.
 */
public class PaneController {

    /**
     * The pane service.
     */
    private PaneService paneService;

    /**
     * The actual state of the controller
     */
    private State currentState;

    /**
     * Constructs a new PaneController.
     */
    public PaneController(PaneService paneService) {
        this.paneService = paneService;
    }

    /**
     * handles the right click.
     * @param intersection the intersection
     */
    public void leftClick(Intersection intersection){
        //currentState.leftClick(this, intersection,new CityMap(null, null));
    }

    /**
     * returns the x position of the intersection.
     * @param i the intersection
     * @param paneWidth the pane width
     * @return the x position of the intersection
     */
    public double getIntersectionX(Intersection i, double paneWidth){
        return paneService.longitudeToX(i.getLongitude(), paneWidth);
    }

    /**
     * returns the y position of the intersection.
     * @param i the intersection
     * @param paneHeight the pane height
     * @return the y position of the intersection
     */
    public double getIntersectionY(Intersection i, double paneHeight){
        return paneService.latitudeToY(i.getLatitude(), paneHeight);
    }

    /**
     * updates the scale of the pane with the intersections
     * @param intersectionList the list of intersections
     */
    public void updateScale(Set<Intersection> intersectionList) {
        paneService.setMinX(Double.MAX_VALUE);
        paneService.setMaxX(Double.MIN_VALUE);
        paneService.setMinY(Double.MAX_VALUE);
        paneService.setMaxY(Double.MIN_VALUE);

        // Fill intersectionsMap (hashmap) while parsing
        for (Intersection inter : intersectionList) {
            paneService.setMinX(Math.min(paneService.getMinX(), inter.getLongitude()));
            paneService.setMaxX(Math.max(paneService.getMaxX(), inter.getLongitude()));
            paneService.setMinY(Math.min(paneService.getMinY(), inter.getLatitude()));
            paneService.setMaxY(Math.max(paneService.getMaxY(), inter.getLatitude()));

        }

        // Compute scaling factors
        paneService.setxRange(paneService.getMaxX() - paneService.getMinX());
        paneService.setyRange(paneService.getMaxY() - paneService.getMinY());
    }
}
