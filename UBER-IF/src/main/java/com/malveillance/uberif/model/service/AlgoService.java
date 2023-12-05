package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.algo.TSP;
import com.malveillance.uberif.model.algo.TSP1;
import javafx.util.Pair;

import java.util.List;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Map;

public class AlgoService {
    private static CityMap createSubGraph(CityMap cityMap, List<Intersection> intersections) {
        CityMap subGraph = new CityMap(cityMap.getWarehouse(), intersections);
        for (Intersection intersection : subGraph.getNodes().keySet()) {
            subGraph.setAdjacentRoads(intersection, cityMap.getAdjacentRoads(intersection));
        }
        return subGraph;
    }

    private static CityMap makeCompleteGraph(CityMap cityMap) {
        List<Intersection> intersections = new ArrayList<>(cityMap.getNodes().keySet());
        CityMap completeGraph = new CityMap(cityMap.getWarehouse(), intersections);
        for (Intersection source : cityMap.getNodes().keySet()) {
            for (Intersection destination : cityMap.getNodes().keySet()) {
                if (!source.equals(destination) && !cityMap.hasRoadSegment(source, destination)) {
                    completeGraph.addRoadSegment(new RoadSegment(source, destination, CityMap.INFINITE_LENGTH, ""));
                } else if (cityMap.hasRoadSegment(source, destination)) {
                    completeGraph.addRoadSegment(new RoadSegment(source, destination, cityMap.getDistance(source, destination), ""));
                }
            }
        }
        return completeGraph;
    }

    private static double dijkstra(CityMap graph, Intersection start, Intersection end) {
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
                return distances.get(end);
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
        return Double.MAX_VALUE;
    }

    public static void calculateOptimalRoute(CityMap cityMap, Tour tour) {
        System.out.println("START");
        List<Intersection> allIntersections = new ArrayList<>();
        List<Delivery> deliveries = tour.getDeliveries();

        for (Delivery delivery : deliveries) {
            allIntersections.add(delivery.getIntersection());
        }

        CityMap subGraph = createSubGraph(cityMap, allIntersections);
        CityMap completeSubGraph = makeCompleteGraph(subGraph);

        /*System.out.println("SUBGRAPH");
        System.out.println(subGraph);

        System.out.println("COMPLETESUBGRAPH");
        System.out.println(completeSubGraph);*/

        for (Delivery deliveryA : deliveries) {
            for (Delivery deliveryB : deliveries) {
                Intersection start = deliveryA.getIntersection();
                Intersection end = deliveryB.getIntersection();
                if (completeSubGraph.hasRoadSegment(start, end) && completeSubGraph.getDistance(start, end) == CityMap.INFINITE_LENGTH) {
                    // TODO : Dijkstra also returns the list of used roadSegments between 2 points
                    double val = dijkstra(cityMap, start, end);
                    completeSubGraph.setDistance(start, end, val);
                }
            }
        }

        /*System.out.println("COMPLETESUBGRAPH AFTER DIJKSTRA");
        System.out.println(completeSubGraph);*/

        TSP tsp = new TSP1();
        long startTime = System.currentTimeMillis();
        tsp.searchSolution(20000, completeSubGraph);
        System.out.print("Solution of cost " + tsp.getSolutionCost() + " found in "
                + (System.currentTimeMillis() - startTime) + "ms : ");
        for (int i = 0; i < completeSubGraph.getNbNodes(); i++)
            System.out.print(tsp.getSolution(i) + " ");
        System.out.println(completeSubGraph.getWarehouse().getIntersection());
        System.out.println("END");
    }

}
