package com.malveillance.uberif.model.service;

public class PaneService {

    /**
     * Default constructor
     */
    public PaneService() {
    }


    // Scaling factors
    private double minX = Double.MAX_VALUE;
    private double maxX = Double.MIN_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;
    private double xRange = .0;
    private double yRange = .0;

    /**
     * Convert longitude to X position in pixels
     * @param longitude The longitude of the intersection
     * @param paneWidth The width of the pane
     * @return The X position of the intersection in pixels
     */
    public double longitudeToX(double longitude, double paneWidth){
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    /**
     * Convert latitude to Y position in pixels
     * @param latitude The latitude of the intersection
     * @param paneHeight The height of the pane
     * @return The Y position of the intersection in pixels
     */
    public double latitudeToY(double latitude, double paneHeight){
        return ( (maxY - latitude) / yRange ) * paneHeight;
    }

    /**
     * @return the minimum X value
     */
    public double getMinX() {
        return minX;
    }

    /**
     * @param minX the minimum X value to set
     */
    public void setMinX(double minX) {
        this.minX = minX;
    }

    /**
     * @return the maximum X value
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * @param maxX the maximum X value to set
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * @return the minimum Y value
     */
    public double getMinY() {
        return minY;
    }

    /**
     * @param minY the minimum Y value to set
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     * @return the maximum Y value
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * @param maxY the maximum Y value to set
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    /**
     * @return the X range
     */
    public double getxRange() {
        return xRange;
    }

    /**
     * @param xRange the X range to set
     */
    public void setxRange(double xRange) {
        this.xRange = xRange;
    }

    /**
     * @return the Y range
     */
    public double getyRange() {
        return yRange;
    }

    /**
     * @param yRange the Y range to set
     */
    public void setyRange(double yRange) {
        this.yRange = yRange;
    }
}
