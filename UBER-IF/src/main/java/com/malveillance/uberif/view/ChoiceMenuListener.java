package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
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

        if (newCityMap != null) {
            graphicalView.update(newCityMap, newCityMap.getNodes());
        } else {
            // Handle the case where the city map is not found or invalid
            System.out.println("City map not found for: " + newValue);
        }
    }

}
