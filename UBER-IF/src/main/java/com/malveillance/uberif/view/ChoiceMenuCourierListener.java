package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

import java.util.List;


public class ChoiceMenuCourierListener implements ChangeListener<String> {

    private GraphicalView graphicalView ;

    public ChoiceMenuCourierListener(GraphicalView graphicalView) {
        this.graphicalView = graphicalView ;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("Courier selected: " + newValue);
        Pair<Courier, List<Intersection>> selectedCourier = null ;
        for (Pair<Courier, List<Intersection>> pair:graphicalView.getListCourierIntersec()) {
            if (pair.getKey().getName().equals(newValue)) {
                selectedCourier = pair ;
                break;
            }
        }
        graphicalView.setSelectedCourier(selectedCourier);

    }

}
