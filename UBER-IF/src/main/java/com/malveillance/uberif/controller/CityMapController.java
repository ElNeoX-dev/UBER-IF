package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.service.CityMapService;

public class CityMapController {

    CityMapService cityMapService;
    public CityMapController(CityMapService cityMapService) {
        this.cityMapService = cityMapService;
    }

    public CityMap loadNewCityMap(String mapName) {
        // Load corresponding map
        return cityMapService.loadMap(mapName);
    }
}
