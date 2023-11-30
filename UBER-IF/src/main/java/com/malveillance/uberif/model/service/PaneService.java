package com.malveillance.uberif.model.service;

public class PaneService {

    public PaneService() {
    }


    // Scaling factors
    public static double minX = Double.MAX_VALUE;
    public static double maxX = Double.MIN_VALUE;
    public static double minY = Double.MAX_VALUE;
    public static double maxY = Double.MIN_VALUE;
    public static double xRange = .0;
    public static double yRange = .0;

    // Method to map latitude to X position
    public double longitudeToX(double longitude, double paneWidth){
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    // Method to map latitude to Y position
    public double latitudeToY(double latitude, double paneHeight){
        return ( (maxY - latitude) / yRange ) * paneHeight;
    }
}
