package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

/**
 * The interface represents a state.
 */
interface State {
    /**
     * The city map.
     */
    public CityMap cityMap = null;

    /**
     * Handles the input.
     * @param context the context
     * @param input the input
     */
    void handleInput(Context context, String input);
}
