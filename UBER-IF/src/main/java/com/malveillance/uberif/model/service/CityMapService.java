package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import com.malveillance.uberif.model.algo.CompleteGraph;
import javafx.util.Pair;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class CityMapService {

    public CityMapService() {

    }

    public CityMap loadMap(String mapName, boolean isSavedMap) {

        CityMap map = null;
        String fileName = mapName;
        XmlMapDeserializer parser = new XmlMapDeserializer();

        try {
            if (isSavedMap) {
                System.out.println(fileName);
                parser.deserialize(this.getClass().getResource(fileName).toURI());
                map = new CityMap(parser.getWarehouse(), parser.getTourCourierPairList(), parser.getIntersectionsElements(), fileName, true);
            } else {
                // Split the mapName and take the first word
                String[] words = mapName.split("\\s+");
                fileName = words[0].toLowerCase();

                // Parse the XML file and add items to the ListView
                URL test = this.getClass().getResource(fileName+"Map.xml");
                System.out.println(test);
                parser.deserialize(this.getClass().getResource(fileName + "Map.xml").toURI());


                map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements(), fileName);
            }
        } catch (URISyntaxException | NullPointerException e) {
            System.err.println("Error occured when loading map "+ fileName + "\n" + e);
        }

        return map;
    }



}
