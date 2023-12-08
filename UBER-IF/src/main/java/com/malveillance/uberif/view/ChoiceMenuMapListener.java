package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


public class ChoiceMenuMapListener implements ChangeListener<String> {

    private GraphicalView graphicalView;
    private CityMapController cityMapController;

    private PaneController paneController;

    public ChoiceMenuMapListener(CityMapController cityMapController, GraphicalView graphicalView, PaneController paneController) {
        this.cityMapController = cityMapController;
        this.paneController = paneController;
        this.graphicalView = graphicalView;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("New map: " + newValue);

        CityMap newCityMap = cityMapController.loadNewCityMap(newValue);
        paneController.updateScale(newCityMap.getNodes().keySet());
        /* test Dijkstra */
        if (newValue.equals("Large Map")) {
            TimeWindow timeWindow = new TimeWindow(120);
            Delivery baseDelivery = new Delivery(newCityMap.getWarehouse().getIntersection(),timeWindow);
            Tour tour = new Tour(baseDelivery);
            CityMap tourTest = cityMapController.loadNewCityMap("tour2");
            //CityMap tourMap = cityMapController.loadNewCityMap("test");


            tourTest.getNodes().keySet().forEach(i -> tour.addDelivery(new Delivery(i,timeWindow)));
            AlgoService.calculateOptimalRoute(newCityMap,tour);
            //AlgoService.calculateOptimalRoute(tourMap,tour);
        }
        /* fin test */

        if (newCityMap != null) {
            graphicalView.update(newCityMap, newCityMap.getNodes());
        } else {
            // Handle the case where the city map is not found or invalid
            System.out.println("City map not found for: " + newValue);
        }
    }

}
