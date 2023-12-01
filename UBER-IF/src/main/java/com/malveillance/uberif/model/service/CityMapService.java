package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import com.malveillance.uberif.model.algo.CompleteGraph;
import javafx.util.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class CityMapService {

    public CityMapService() {

    }

    public CityMap loadMap(String mapName){
        // Split the mapName and take the first word
        String[] words = mapName.split("\\s+");
        String fileName = words[0].toLowerCase();

        // Parse the XML file and add items to the ListView
        XmlMapDeserializer parser = new XmlMapDeserializer("src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
        CityMap map;
        map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements(), fileName);

        return map;
    }



}
