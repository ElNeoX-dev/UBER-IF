package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;

class InitialState implements State {
    @Override
    public void handleInput(Context context, String input) {
        if (input.equals("selectCourier")) {
            //context.setState(new SelectState());
        }
        // Other input handling for InitialState
    }
}