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

    public Map<Intersection, Pair<Intersection, Double>> makeCompleteGraph(CityMap cityMap) {
        Map<Intersection,Pair<Intersection, Double>> completeGraph = new HashMap<>();
        for (Intersection source : cityMap.getNodes().keySet()) {
            for (Intersection destination : cityMap.getNodes().keySet()) {
                if (!source.equals(destination) && !cityMap.hasRoadSegment(source, destination)) {
                    completeGraph.put(source,new Pair<>(destination,CityMap.INFINITE_LENGTH));
                } else if (cityMap.hasRoadSegment(source,destination))
                {
                    completeGraph.put(source,new Pair<>(destination,cityMap.getDistance(source,destination)));
                }
            }
        }
        return completeGraph;
    }

    public void calculateOptimalRoute(Warehouse warehouse , CityMap cityMap,Tour tour) {
        List<Intersection> allIntersections = new ArrayList<>();
        List<RoadSegment> allSegments = new ArrayList<>();

        List<Delivery> deliveries = tour.getDeliveries();

        for (Delivery delivery : deliveries) {
            allIntersections.add(delivery.getIntersection());
        }

        CityMap optimalRouteMap = new CityMap(warehouse, allIntersections, null);
        optimalRouteMap = makeCompleteGraph();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Intersection start = deliveries.get(i).getIntersection();
                Intersection end = deliveries.get(j).getIntersection();
                double length;
                if (cityMap.getSegmentLength(start, end) == CityMap.INFINITE_LENGTH) {
                    length = Dijkstra.findShortestPathLength(cityMap, start, end);
                else{
                        length = cityMap.getDistance(start, end);
                    }
                    optimalRouteMap.setDistance(start, end, length);
                }
            }

        }
    }
}
