package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;

public class AdressState implements State{
    public AdressState(){};

    @Override
    public void leftClick(PaneController paneController, Intersection intersection, CityMap cityMap) {
        if (!cityMap.IntersectionInMap(intersection)){

        }
    }
}
