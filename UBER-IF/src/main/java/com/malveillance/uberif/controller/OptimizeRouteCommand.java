package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import com.malveillance.uberif.view.GraphicalView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class represents a command to optimize the route.
 */
public class OptimizeRouteCommand implements Command {
    /**
     * The graphical view associated with the command.
     */
    private GraphicalView graphicalView;
    /**
     * The context managing the state transitions.
     */
    private Context context;

    /**
     * Constructs a new OptimizeRouteCommand
     * @param graphicalView the graphical view associated with the command
     * @param context       the context managing the state transitions
     */
    public OptimizeRouteCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    /**
     * Executes the command in order to optimize the routes.
     */
    @Override
    public void execute() {

        // CityMap previousCityMap = cityMap.deepCopy();

        graphicalView.getMapPane().getChildren().clear();
        graphicalView.update(graphicalView.getCityMap(), graphicalView.getCityMap().getNodes());
        for(Courier courier : graphicalView.getCityMap().getCourierDotMap().keySet()) {
            if(!courier.getName().isEmpty()) {
                List<Pair<Intersection, TimeWindow>> deliveryPoints = graphicalView.getCityMap().getSelectedPairList(courier);
                Tour tour = new Tour(new Delivery(graphicalView.getCityMap().getWarehouse().getIntersection(), new TimeWindow(0)));
                for (Pair<Intersection, TimeWindow> d : deliveryPoints) {
                    tour.addDelivery(new Delivery(d.getKey(), d.getValue()));
                }
                courier.setCurrentTour(tour);
                List<Pair<Intersection, Date>> computedTravel = AlgoService.calculateOptimalRoute(graphicalView.getCityMap(), tour);
                graphicalView.getCityMap().addTravelPlan(courier, computedTravel);
            }
        }
        context.handleInput("optimize", graphicalView);
    }
}

