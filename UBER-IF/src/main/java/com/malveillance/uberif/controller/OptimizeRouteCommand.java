package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * The class represents a command to optimize the route.
 */
public class OptimizeRouteCommand implements Command {

    /**
     * The city map.
     */
    private CityMap cityMap;

    /**
     * The previous state.
     */
    private CityMap previousState;

    /**
     * Constructs a new OptimizeRouteCommand.
     * @param cityMap the city map
     */
    public OptimizeRouteCommand(CityMap cityMap) {
        this.cityMap = cityMap;
        this.previousState = null;
    }

    /**
     * Executes the command in order to optimize the routes.
     */
    @Override
    public void execute() {
        // Backup the current state
        previousState = cityMap.deepCopy(); // Assuming deepCopy() method exists

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

    /**
     * Undoes the command.
     */
    @Override
    public void undo() {
        if (previousState != null) {
            // Restore the previous state
            cityMap = previousState;
        }
    }
}

