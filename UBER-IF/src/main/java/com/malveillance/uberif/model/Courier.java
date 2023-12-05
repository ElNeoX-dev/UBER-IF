package com.malveillance.uberif.model;

import javafx.scene.paint.Color;

public class Courier {
    private static Long id;
    private Color color ;
    private String name ;

    private Tour currentTour;

    public Courier(String name, Color color) {
        this.name = name ;
        this.color = color ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tour getCurrentTour() {
        return currentTour;
    }

    public void setCurrentTour(Tour currentTour) {
        this.currentTour = currentTour;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", currentTour=" + currentTour +
                '}';
    }
}
