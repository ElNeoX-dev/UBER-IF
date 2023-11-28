package com.malveillance.uberif.model.service;

import com.malveillance.uberif.controller.HelloController;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import javafx.scene.control.ListView;

import java.util.Map;

public class Service {

    private HelloController controller ;

    public Service(HelloController controller) {
        this.controller = controller ;
    }

    public void loadMap(Map<String, Intersection> intersectionMap, String mapName){
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        ListView<String> listView = new ListView<>();

        // Split the mapName and take the first word
        String[] words = mapName.split("\\s+");
        String fileName = words[0].toLowerCase();

        // Parse the XML file and add items to the ListView
        XmlMapDeserializer parser = new XmlMapDeserializer("src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
        parser.mapElements.forEach(element -> listView.getItems().add(element.toString()));

        // Clear existing data
        intersectionMap.clear();

        // Fill intersectionsMap (hashmap) while parsing
        for (Object elem : parser.mapElements){
            if (elem instanceof Intersection inter){
                intersectionMap.put(inter.id, inter);
                minX = Math.min(minX, inter.longitude);
                maxX = Math.max(maxX, inter.longitude);
                minY = Math.min(minY, inter.latitude);
                maxY = Math.max(maxY, inter.latitude);
            }
        }

        // Compute scaling factors
        double xRange = maxX - minX;
        double yRange = maxY - minY;

        redrawElementsOnMap(controller, "src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
    }

    // Method to map latitude to X position
    private static double longitudeToX(double longitude, double paneWidth){
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    // Method to map latitude to Y position
    private static double latitudeToY(double latitude, double paneHeight){
        return ( (maxY - latitude) / yRange ) * paneHeight;
    }

    // Get X position of coordinates
    private static double getIntersectionX(String interId, double paneWidth){
        Intersection i = intersectionMap.get(interId);

        if (i != null){
            return longitudeToX(i.longitude, paneWidth);
        }
        return .0;
    }

    // Get Y position of coordinates
    private static double getIntersectionY(String interId, double paneWidth){
        Intersection i = intersectionMap.get(interId);

        if (i != null){
            return latitudeToY(i.latitude, paneWidth);
        }
        return .0;
    }


}
