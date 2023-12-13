package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;

class SelectState implements State {
    public SelectState(CityMap cityMap){
        this.cityMap = cityMap;
    }
    @Override
    public void handleInput(Context context, String input) {
        if (input.equals("addAddress")) {
            // Code to add address
        }
        // Other input handling for AddressState
    }
}
