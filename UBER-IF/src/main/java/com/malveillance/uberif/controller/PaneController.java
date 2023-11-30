package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.service.PaneService;

public class PaneController {

    private PaneService paneService;
    public PaneController(PaneService paneService) {
        this.paneService = paneService;
    }

    public double getIntersectionX(Intersection i, double paneWidth){
        return paneService.longitudeToX(i.getLongitude(), paneWidth);
    }

    // Method to map latitude to Y position
    public double getIntersectionY(Intersection i, double paneHeight){
        return paneService.latitudeToY(i.getLatitude(), paneHeight);
    }
}
