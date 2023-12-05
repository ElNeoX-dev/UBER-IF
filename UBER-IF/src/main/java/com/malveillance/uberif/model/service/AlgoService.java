package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.algo.TSP;
import com.malveillance.uberif.model.algo.TSP1;
import javafx.util.Pair;

import java.util.*;
import java.util.Map;

public class AlgoService {
    private static double courierSpeed = 15 / 3.6;

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

    private static Pair<Double, List<Intersection>> dijkstra(CityMap graph, Intersection start, Intersection end) {
        Map<Intersection, Double> distances = new HashMap<>();
        Map<Intersection, Intersection> previous = new HashMap<>();
        PriorityQueue<Intersection> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        List<Intersection> path = new ArrayList<>();

        for (Intersection vertex : graph.getNodes().keySet()) {
            distances.put(vertex, Double.MAX_VALUE);
            previous.put(vertex, null);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Intersection current = queue.poll();
            if (current.equals(end)) {
                double totalDistance = distances.get(end);
                while (current != null) {
                    path.add(0, current);
                    current = previous.get(current);
                }
                return new Pair<>(totalDistance, path);
            }

            for (RoadSegment edge : graph.getAdjacentRoads(current)) {
                Intersection neighbor = edge.getDestination();
                double totalDistance = distances.get(current) + edge.getLength();
                if (totalDistance < distances.get(neighbor)) {
                    distances.put(neighbor, totalDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return new Pair<>(Double.MAX_VALUE, new ArrayList<>());
    }

    private static CityMap convertToTimeGraph(CityMap graph) {
        graph.getNodes().forEach((node,roadSegments) ->
                roadSegments.forEach(roadSegment ->
                        roadSegment.setLength(roadSegment.getLength() / courierSpeed)));
        return graph;
    }

    public static void calculateOptimalRoute(CityMap cityMap, Tour tour) {
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

        Map<Pair<Intersection, Intersection>, List<Intersection>> allPaths = new HashMap<>();

        for (Delivery deliveryA : deliveries) {
            for (Delivery deliveryB : deliveries) {
                Intersection start = deliveryA.getIntersection();
                Intersection end = deliveryB.getIntersection();
                if (!start.equals(end) && completeSubGraph.hasRoadSegment(start, end)) {
                    Pair<Double, List<Intersection>> result = dijkstra(cityMap, start, end);
                    double distance = result.getKey();
                    List<Intersection> path = result.getValue();
                    if (!path.isEmpty()) {
                        completeSubGraph.setDistance(start, end, distance);
                        allPaths.put(new Pair<>(start, end), path);
                    }
                }
            }
        }

        /* System.out.println("COMPLETESUBGRAPH AFTER DIJKSTRA");
        System.out.println(completeSubGraph);*/

        TSP tsp = new TSP1();
        long startTime = System.currentTimeMillis();
        tsp.searchSolution(20000, completeSubGraph, tour);
        System.out.print("Solution of cost " + tsp.getSolutionCost() + " found in " + (System.currentTimeMillis() - startTime) + "ms : ");

        Intersection firstIntersection = completeSubGraph.getWarehouse().getIntersection();
        Intersection prev = firstIntersection;
        List<Pair<Intersection,Pair<Date,Date>>> finalTour = new LinkedList<>();
        finalTour.add(new Pair<>(firstIntersection,null));

        for (int i = 1; i < completeSubGraph.getNbNodes(); i++) {
            Intersection next = tsp.getSolution(i).getIntersection();
            List<Intersection> path = allPaths.getOrDefault(new Pair<>(prev, next), Arrays.asList(prev, next));
            for (int j = 1; j < path.size(); j++) {
                Intersection intersection = path.get(j);
                finalTour.add(new Pair<>(intersection,null));
            }
            prev = next;
        }
        finalTour.add(new Pair<>(firstIntersection,null));

        finalTour.forEach(obj -> System.out.print(obj.getKey().getId()+" -> "));
    }

}
