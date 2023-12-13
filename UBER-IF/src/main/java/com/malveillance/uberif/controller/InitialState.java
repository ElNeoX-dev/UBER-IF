package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;

class InitialState implements State {

    private CityMap cityMap = null;

    public InitialState(CityMap cityMap){
        this.cityMap = cityMap;
    }
    @Override
    public void handleInput(Context context, String input) {
        if (input.equals("selectCourier")) {
            context.setState(new SelectState(this.cityMap));
        }
        // Other input handling for InitialState
    }
}