package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;

/**
 * The class represents the SelectState.
 */
class SelectState implements State {

    /**
     * Constructs a new SelectState.
     */
    public SelectState(CityMap cityMap){
       // this.cityMap = cityMap;
    }

    /**
     * Handles the input.
     * @param context the context
     * @param input the input
     */
    @Override
    public void handleInput(Context context, String input) {
        if (input.equals("addAddress")) {
            // Code to add address
        }
        // Other input handling for AddressState
    }
}
