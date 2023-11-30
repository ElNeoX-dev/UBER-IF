package com.malveillance.uberif.model.service;

import com.malveillance.uberif.controller.HelloController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import javafx.scene.control.ListView;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Service {


    public Service(){
    }

    // Scaling factors
    public static double minX = Double.MAX_VALUE;
    public static double maxX = Double.MIN_VALUE;
    public static double minY = Double.MAX_VALUE;
    public static double maxY = Double.MIN_VALUE;
    public static double xRange = .0;
    public static double yRange = .0;

    public CityMap loadMap(String mapName){
        /*
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        */

        // Split the mapName and take the first word
        String[] words = mapName.split("\\s+");
        String fileName = words[0].toLowerCase();

        // Parse the XML file and add items to the ListView
        XmlMapDeserializer parser = new XmlMapDeserializer("src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
        CityMap map;
        map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements());

        // Fill intersectionsMap (hashmap) while parsing
        /*
        for (Object elem : parser.mapElements){
            if (elem instanceof Intersection inter){
                minX = Math.min(minX, inter.longitude);
                maxX = Math.max(maxX, inter.longitude);
                minY = Math.min(minY, inter.latitude);
                maxY = Math.max(maxY, inter.latitude);
            }
        }

        // Compute scaling factors
        double xRange = maxX - minX;
        double yRange = maxY - minY;
        */

        return map;
    }

    // Method to map latitude to X position
    public double longitudeToX(double longitude, double paneWidth){
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    // Method to map latitude to Y position
    public double latitudeToY(double latitude, double paneHeight){
        return ( (maxY - latitude) / yRange ) * paneHeight;
    }

    // Get X position of coordinates
    public double getIntersectionX(Intersection i, double paneWidth){
        if (i != null){
            return longitudeToX(i.getLongitude(), paneWidth);
        }
        return .0;
    }

    // Get Y position of coordinates
    public double getIntersectionY(Intersection i, double paneHeight){
        if (i != null){
            return latitudeToY(i.getLatitude(), paneHeight);
        }
        return .0;
    }

    public static double findShortestPathLength(CityMap graph, Intersection start, Intersection end) {
        Map<Intersection, Double> distances = new HashMap<>();
        Map<Intersection, Intersection> previous = new HashMap<>();
        PriorityQueue<Intersection> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        for (Intersection vertex : graph.getNodes().keySet()) {
            distances.put(vertex, Double.MAX_VALUE);
            previous.put(vertex, null);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Intersection current = queue.poll();

            if (current.equals(end)) {
                return distances.get(end); // Return the total distance to the end node
            }

            for (RoadSegment edge : graph.getAdjacentRoads(current)) {
                Intersection neighbor = edge.getDestination();
                Double totalDistance = distances.get(current) + edge.getLength();

                if (totalDistance < distances.get(neighbor)) {
                    distances.put(neighbor, totalDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Double.MAX_VALUE; // Return maximum value if path not found
    }
}
