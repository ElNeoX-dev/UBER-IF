package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.algo.TSP;
import com.malveillance.uberif.model.algo.TSP1;
import javafx.util.Pair;

import java.util.*;
import java.util.Map;

/**
 * This class provides algorithms for calculating optimal routes for delivery services.
 */
public class AlgoService {
    private static double courierSpeed = 15 / 3.6;

    /**
     * Creates a subgraph of a given city map, including only specified intersections
     * @param cityMap The original city map
     * @param intersections The list of intersections to include in the subgraph
     * @return A new CityMap object representing the subgraph
     */
    private static CityMap createSubGraph(CityMap cityMap, List<Intersection> intersections) {
        CityMap subGraph = new CityMap(cityMap.getWarehouse(), intersections);
        for (Intersection intersection : subGraph.getNodes().keySet()) {
            subGraph.setAdjacentRoads(intersection, cityMap.getAdjacentRoads(intersection));
        }
        return subGraph;
    }

    /**
     * Converts a given graph map into a complete graph
     * @param cityMap The original city map
     * @return A new CityMap object representing the complete graph
     */
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

    /**
     * Dijkstra's algorithm to find the shortest path between two intersections
     * @param graph The graph to search in
     * @param start The starting intersection
     * @param end The ending intersection
     * @return A Pair containing the total distance and a list of intersections forming the shortest path
     */
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

    /**
     * Converts a city map graph into a time graph, adjusting road lengths based on courier speed
     * @param graph The original city map graph
     * @return The city map graph with road lengths adjusted for time
     */
    private static CityMap convertToTimeGraph(CityMap graph) {
        graph.getNodes().forEach((node, roadSegments) ->
                roadSegments.forEach(roadSegment ->
                        roadSegment.setLength(roadSegment.getLength() / courierSpeed)));
        return graph;
    }

    /**
     * Calculates the optimal route for a set of deliveries using TSP algorithm
     * @param cityMap The city map to calculate the route
     * @param tour The tour containing a list of deliveries
     * @return A list of pairs, containing an intersection and the estimated arrival date
     */
    public static List<Pair<Intersection, Date>> calculateOptimalRoute(CityMap cityMap, Tour tour) {
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
        CityMap completeSubTimeGraph = convertToTimeGraph(completeSubGraph);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, TimeWindow.defaultStartingHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startingTime = calendar.getTime();
        tsp.searchSolution(20000, completeSubTimeGraph, tour, startingTime);
        //System.out.println("Solution of cost " + tsp.getSolutionCost() + " found in " + (System.currentTimeMillis() - startTime) + "ms");

        Intersection firstIntersection = completeSubGraph.getWarehouse().getIntersection();
        Intersection prev = firstIntersection;
        List<Pair<Intersection, Date>> finalTour = new LinkedList<>();
        Date arrivalDate = null;

        try {
            for (int i = 0; i < completeSubGraph.getNbNodes(); i++) {
                Pair<Intersection, Date> delivery = tsp.getSolution(i);
                Intersection next = delivery.getKey();
                arrivalDate = delivery.getValue();
                List<Intersection> path = allPaths.getOrDefault(new Pair<>(prev, next), Arrays.asList(prev, next));

                for (int j = 1; j < path.size() - 1; j++) {
                    Intersection intersection = path.get(j);
                    finalTour.add(new Pair<>(intersection, null));
                }
                finalTour.add(new Pair<>(path.get(path.size() - 1), arrivalDate));
                prev = next;
            }
            List<Intersection> path = allPaths.getOrDefault(new Pair<>(prev, firstIntersection), Arrays.asList(prev, firstIntersection));
            double distanceToWarehouse = completeSubGraph.getDistance(prev, firstIntersection);
            for (int j = 1; j < path.size() - 1; j++) {
                finalTour.add(new Pair<>(path.get(j), null));
            }
            if (arrivalDate != null) {
                long fiveMinutesInMilliseconds = 5 * 60 * 1000; // 5 minutes in milliseconds
                long distanceToWarehouseInMilliseconds = (long) (distanceToWarehouse * 1000); // Distance in milliseconds
                Date warehouseArrivalTime = new Date(arrivalDate.getTime() + fiveMinutesInMilliseconds + distanceToWarehouseInMilliseconds);
                finalTour.add(new Pair<>(firstIntersection, warehouseArrivalTime));
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("No possible solution");
            return null;
        }


        /*finalTour.forEach(obj -> {
            System.out.print(obj.getKey().getId());
            Date date = obj.getValue();
            if (date != null)
                System.out.print(" at " + date);
            System.out.print(" -> ");
        });*/
        return finalTour;
    }
}