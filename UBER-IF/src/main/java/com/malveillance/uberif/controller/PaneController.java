package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.model.service.PaneService;

import java.util.Set;

public class PaneController {

    private PaneService paneService;
    private State currentState;
    public PaneController(PaneService paneService) {
        this.paneService = paneService;
    }

    public void leftClick(Intersection intersection){
        //currentState.leftClick(this, intersection,new CityMap(null, null));
    }
    public double getIntersectionX(Intersection i, double paneWidth){
        return paneService.longitudeToX(i.getLongitude(), paneWidth);
    }

    // Method to map latitude to Y position
    public double getIntersectionY(Intersection i, double paneHeight){
        return paneService.latitudeToY(i.getLatitude(), paneHeight);
    }

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
