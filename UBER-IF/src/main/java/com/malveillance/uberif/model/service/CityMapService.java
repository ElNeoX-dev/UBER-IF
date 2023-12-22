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

    /**
     * Load the XML file that corresponds to the mapName
     * @param mapName The name of the map
     * @param isSavedMap Whether the map is saved or not
     * @return A CityMap object linked to the XML file
     */
    public CityMap loadMap(String mapName, boolean isSavedMap ){

        CityMap map;
        String fileName = mapName;
        XmlMapDeserializer parser = new XmlMapDeserializer();

        if (isSavedMap) {
            parser.deserialize(fileName);
            map = new CityMap(parser.getWarehouse(), parser.getTourCourierPairList(), parser.getIntersectionsElements(), fileName, true);
        }else {
            // Split the mapName and take the first word
            String[] words = mapName.split("\\s+");
            fileName = words[0].toLowerCase();

            // Parse the XML file and add items to the ListView
            parser.deserialize("src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
            map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements(), fileName);
        }

        return map;
    }



}
