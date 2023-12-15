package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import com.malveillance.uberif.view.GraphicalView;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


public class OptimizeRouteCommand implements Command {
    private GraphicalView graphicalView;
    private Context context;

    public OptimizeRouteCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    @Override
    public void execute() {

        // CityMap previousCityMap = cityMap.deepCopy();


        // System.out.println("Optimize click");
        for(Courier courier : graphicalView.getCityMap().getCourierDotMap().keySet()) {
            if(!courier.getName().isEmpty()) {
                List<Pair<Intersection, TimeWindow>> deliveryPoints = graphicalView.getCityMap().getSelectedPairList(courier);

                Tour tour = new Tour(new Delivery(graphicalView.getCityMap().getWarehouse().getIntersection(), new TimeWindow(0)));
                for(Pair<Intersection, TimeWindow> d : deliveryPoints) {
                    tour.addDelivery(new Delivery(d.getKey(), d.getValue()));
                }
                courier.setCurrentTour(tour);
                List<Pair<Intersection, Date>> computedTravel = AlgoService.calculateOptimalRoute(graphicalView.getCityMap(), tour);
                graphicalView.getCityMap().addTravelPlan(courier, computedTravel);
                }
            }
        InitialState newState = new InitialState(graphicalView);
        context.setState(newState);
        }



//    @Override
//    public void undo() {
//        if (previousState != null) {
//            // Restore the previous state
//
//        }
//    }
}

