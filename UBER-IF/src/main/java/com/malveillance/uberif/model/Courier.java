package com.malveillance.uberif.model;

public class Courier {
    private Long id;

    private Tour currentTour;

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

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", currentTour=" + currentTour +
                '}';
    }
}
