package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import com.malveillance.uberif.util.ResourceReader;

import java.io.InputStream;

public class CityMapService {

    public CityMapService() {

    }

    /**
     * Load the XML file that corresponds to the mapName
     * @param mapName The name of the map
     * @param isSavedMap Whether the map is saved or not
     * @return A CityMap object linked to the XML file
     */
    public CityMap loadMap(String mapName, boolean isSavedMap){

        CityMap map = null;
        String fileName = mapName;
        XmlMapDeserializer parser = new XmlMapDeserializer();
        ResourceReader rsReader = new ResourceReader();

        try {
            if (isSavedMap) {
                InputStream is = rsReader.getFileAsIOStream(fileName);
                parser.deserialize(is);
                map = new CityMap(parser.getWarehouse(), parser.getTourCourierPairList(), parser.getIntersectionsElements(), fileName, true);
            } else {
                // Split the mapName and take the first word
                String[] words = mapName.split("\\s+");
                fileName = words[0].toLowerCase();

                InputStream is = rsReader.getFileAsIOStream(fileName + "Map.xml");
                parser.deserialize(is);


                map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements(), fileName);
            }
        } catch (NullPointerException e) {
            System.err.println("Error occured when loading map "+ fileName + "\n" + e);
        }

        return map;
    }



}
