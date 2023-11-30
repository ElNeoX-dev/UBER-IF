package com.malveillance.uberif.model.service;

public class PaneService {

    public PaneService() {
    }


    // Scaling factors
    private double minX = Double.MAX_VALUE;
    private double maxX = Double.MIN_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;
    private double xRange = .0;
    private double yRange = .0;

    // Method to map latitude to X position
    public double longitudeToX(double longitude, double paneWidth){
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    // Method to map latitude to Y position
    public double latitudeToY(double latitude, double paneHeight){
        return ( (maxY - latitude) / yRange ) * paneHeight;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getxRange() {
        return xRange;
    }

    public void setxRange(double xRange) {
        this.xRange = xRange;
    }

    public double getyRange() {
        return yRange;
    }

    public void setyRange(double yRange) {
        this.yRange = yRange;
    }
}
