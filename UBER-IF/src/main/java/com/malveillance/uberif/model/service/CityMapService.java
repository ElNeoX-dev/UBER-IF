package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.xml.XmlMapDeserializer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

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
        map = new CityMap(parser.getWarehouse(), parser.getIntersectionsElements(), parser.getSegmentElements());

        return map;
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
