package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


public class OptimizeRouteCommand implements Command {
    private CityMap cityMap;
    private CityMap previousState;

    public OptimizeRouteCommand(CityMap cityMap) {
        this.cityMap = cityMap;
        this.previousState = null;
    }

    @Override
    public void execute() {
        // Backup the current state
        // previousState = cityMap.deepCopy(); // Assuming deepCopy() method exists

        System.out.println("Optimize click");
        for(Courier courier : cityMap.getCourierDotMap().keySet()) {
            if(!courier.getName().isEmpty()) {
                List<Pair<Intersection, TimeWindow>> deliveryPoints = cityMap.getSelectedPairList(courier);

                Tour tour = new Tour(new Delivery(cityMap.getWarehouse().getIntersection(), new TimeWindow(0)));
                for(Pair<Intersection, TimeWindow> d : deliveryPoints) {
                    tour.addDelivery(new Delivery(d.getKey(), d.getValue()));
                }
                courier.setCurrentTour(tour);
                List<Pair<Intersection, Date>> computedTravel = AlgoService.calculateOptimalRoute(cityMap, tour);
                cityMap.addTravelPlan(courier, computedTravel);
                }
            }
        }

    public CityMap getCityMap() {
        return cityMap;
    }

    @Override
    public void undo() {
        if (previousState != null) {
            // Restore the previous state
            cityMap = previousState;
        }
    }
}

