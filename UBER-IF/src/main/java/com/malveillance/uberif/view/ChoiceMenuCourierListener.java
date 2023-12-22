package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.TimeWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * The class represents a listener for the choice menu of couriers.
 */
public class ChoiceMenuCourierListener implements ChangeListener<String> {

    /**
     * The graphical view.
     */
    private GraphicalView graphicalView;

    /**
     * Constructs a new ChoiceMenuCourierListener with the specified graphical view.
     * @param graphicalView the graphical view
     */
    public ChoiceMenuCourierListener(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    /**
     * Handles the courier selection event.
     * @param observable the observable object
     * @param oldValue the old value
     * @param newValue the new value
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // System.out.println("Courier selected: " + newValue);
        Pair<Courier, List<Pair<Intersection, TimeWindow>>> selectedCourier = null;
        Map<Courier, List<Pair<Intersection, TimeWindow>>> couriersDotMap = graphicalView.getCityMap()
                .getCourierDotMap();

        for (Courier courier : graphicalView.getCityMap().getListCourier()) {
            if (courier.getName().equals(newValue)) {
                selectedCourier = new Pair<>(courier, couriersDotMap.get(courier));
                break;
            }
        }

        graphicalView.setSelectedCourier(selectedCourier);

    }

}
