package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.scene.control.Label;

public interface State {
    void handleInput(Context context, String input);
    public CityMap getCityMap();

    public Courier getSelectedCourier();

    public Label getlbInfos();
}
