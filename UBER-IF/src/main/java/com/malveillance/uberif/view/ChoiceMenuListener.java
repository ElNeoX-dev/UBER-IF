package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.Tour;
import com.malveillance.uberif.model.service.AlgoService;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


public class ChoiceMenuListener implements ChangeListener<String> {

    private GraphicalView graphicalView;
    private CityMapController cityMapController;

    private PaneController paneController;

    public ChoiceMenuListener(CityMapController cityMapController, GraphicalView graphicalView, PaneController paneController) {
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
//        if (newValue.equals("Small Map")) {
//            Tour tour = new Tour();
//            for (Intersection i : newCityMap.getNodes().keySet()) {
//                tour.addDelivery(new Delivery(i, null, null));
//            }
//            AlgoService.calculateOptimalRoute(newCityMap,tour);
//        }
        /* fin test */


        if (newCityMap != null) {
            graphicalView.update(newCityMap, newCityMap.getNodes());
        } else {
            // Handle the case where the city map is not found or invalid
            System.out.println("City map not found for: " + newValue);
        }
    }

}
