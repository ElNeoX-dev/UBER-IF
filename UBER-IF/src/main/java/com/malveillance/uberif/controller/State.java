package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

public interface State {
    void handleInput(Context context, String input, GraphicalView graphicalView);

    public GraphicalView getGraphicalView();

    public Courier getSelectedCourier();

    public Label getlbInfos();
}
