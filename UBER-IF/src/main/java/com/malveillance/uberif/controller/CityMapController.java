package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.service.CityMapService;

/**
 * The class represents a controller for the city map.
 */
public class  CityMapController {

    /**
     * Constructs a new CityMapController.
     */
    CityMapService cityMapService;

    /**
     * Constructs a new CityMapController.
     */
    public CityMapController(CityMapService cityMapService) {
        this.cityMapService = cityMapService;
    }

    /**
     * Loads a new city map.
     * @param mapName the name of the map
     * @param isSavedMap true if the map is saved, false otherwise
     * @return the new city map
     */
    public CityMap loadNewCityMap(String mapName, boolean isSavedMap) {
        // Load corresponding map
        return cityMapService.loadMap(mapName, isSavedMap);
    }
}
