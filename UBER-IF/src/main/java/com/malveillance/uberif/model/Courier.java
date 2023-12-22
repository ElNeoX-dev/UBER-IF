/**
 * The class represents a courier entity with a name, color, selected intersection list,
 * and the current tour information.
 */
package com.malveillance.uberif.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Courier {
    /**
     * The color of the courier.
     */
    private Color color;

    /**
     * The name of the courier.
     */
    private String name;

    /**
     * The list of selected intersections by the courier.
     */
    private List<Intersection> selectedDotList = new ArrayList<>();

    /**
     * The current tour associated with the courier.
     */
    private Tour currentTour;

    /**
     * Constructs a new Courier with the specified name and color
     * @param name  the name of the courier
     * @param color the color of the courier
     */
    public Courier(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Constructs a new Courier by copying another Courier
     * @param other the courier to be copied
     */
    public Courier(Courier other) {
        this.name = other.name;
        this.color = other.color;

        // Deep copy of selectedDotList
        this.selectedDotList = new ArrayList<>();
        for (Intersection intersection : other.selectedDotList) {
            this.selectedDotList.add(new Intersection(intersection));
        }

        this.currentTour = other.currentTour != null ? new Tour(other.currentTour) : null;
    }

    /**
     * Returns the list of selected intersections by the courier
     * @return the list of selected intersections
     */
    public List<Intersection> getSelectedIntersectionList() {
        return selectedDotList;
    }

    /**
     * Returns the current tour associated with the courier
     * @return the current tour
     */
    public Tour getCurrentTour() {
        return currentTour;
    }

    /**
     * Sets the current tour for the courier
     * @param currentTour the new current tour
     */
    public void setCurrentTour(Tour currentTour) {
        this.currentTour = currentTour;
    }

    /**
     * Returns the color of the courier
     * @return the color of the courier
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the courier
     * @param color the new color for the courier
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the name of the courier
     * @return the name of the courier
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the courier
     * @param name the new name for the courier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the courier
     * @return a string representation of the courier
     */
    @Override
    public String toString() {
        return "Courier{" +
                "currentTour=" + currentTour +
                '}';
    }
}
