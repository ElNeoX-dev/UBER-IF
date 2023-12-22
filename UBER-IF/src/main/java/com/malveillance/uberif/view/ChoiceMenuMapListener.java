package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

/**
 * The class represents a listener for the choice menu of maps.
 */
public class ChoiceMenuMapListener implements ChangeListener<String> {

    /**
     * The graphical view.
     */
    private GraphicalView graphicalView;

    /**
     * The city map controller.
     */
    private CityMapController cityMapController;

    /**
     * The pane controller.
     */
    private PaneController paneController;

    /**
     * Constructs a new ChoiceMenuMapListener with the specified graphical view.
     *
     * @param graphicalView the graphical view
     */
    public ChoiceMenuMapListener(CityMapController cityMapController, GraphicalView graphicalView,
            PaneController paneController) {
        this.cityMapController = cityMapController;
        this.paneController = paneController;
        this.graphicalView = graphicalView;
    }

    /**
     * Handles the map selection event.
     *
     * @param observable the observable object
     * @param oldValue   the old value
     * @param newValue   the new value
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // System.out.println("New map: " + newValue);

        CityMap newCityMap = cityMapController.loadNewCityMap(newValue, false);
        paneController.updateScale(newCityMap.getNodes().keySet());
        /* test Dijkstra */
        // if (newValue.equals("Small Map")) {
        // Tour tour = new Tour();
        // for (Intersection i : newCityMap.getNodes().keySet()) {
        // tour.addDelivery(new Delivery(i, null, null));
        // }
        // AlgoService.calculateOptimalRoute(newCityMap,tour);
        // }
        /* fin test */

        if (newCityMap != null) {
            graphicalView.update(newCityMap, newCityMap.getNodes());

        } else {
            // Handle the case where the city map is not found or invalid
            System.out.println("City map not found for: " + newValue);
        }
    }

}
