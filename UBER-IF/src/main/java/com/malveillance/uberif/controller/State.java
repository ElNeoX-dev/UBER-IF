package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

interface State {
    void handleInput(Context context, String input);
}
