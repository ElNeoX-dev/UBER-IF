package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;


public class ChoiceMenuCourierListener implements ChangeListener<String> {

    private GraphicalView graphicalView ;

    public ChoiceMenuCourierListener(GraphicalView graphicalView) {
        this.graphicalView = graphicalView ;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //System.out.println("Courier selected: " + newValue);
        Pair<Courier, List<Intersection>> selectedCourier = null ;
        Map<Courier, List<Intersection>> couriersDotMap = graphicalView.getCityMap().getCourierDotMap();

        for (Courier courier : graphicalView.getCityMap().getListCourier()) {
            if (courier.getName().equals(newValue)) {
                selectedCourier = new Pair<>(courier, couriersDotMap.get(courier));
                break;
            }
        }

        graphicalView.setSelectedCourier(selectedCourier);

    }

}
