//package com.malveillance.uberif.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Tour {
//    private List<Delivery> deliveries;
//
//    public List<Delivery> getDeliveries() {
//        return deliveries;
//    }
//
//    public void setDeliveries(List<Delivery> deliveries) {
//        this.deliveries = deliveries;
//    }
//
//    public void calculateOptimalRoute(Warehouse warehouse ,CityMap cityMap) {
//        List<Intersection> allIntersections = new ArrayList<>();
//        List<RoadSegment> allSegments = new ArrayList<>();
//
//        // Initialize the submap with only the intersections involved in deliveries
//        for (Delivery delivery : deliveries) {
//            allIntersections.add(delivery.getIntersection());
//        }
//
//        // Calculate the shortest path for each pair of deliveries
//        for (int i = 0; i < deliveries.size(); i++) {
//            for (int j = i + 1; j < deliveries.size(); j++) {
//                Intersection start = deliveries.get(i).getIntersection();
//                Intersection end = deliveries.get(j).getIntersection();
//
//                // Check if the segment exists and if its length is infinite
//                if (!cityMap.hasRoadSegment(start, end) || cityMap.getSegmentLength(start, end) == CityMap.INFINITE_LENGTH) {
//                    // If length is infinite, use Dijkstra to find the shortest path
//                    double length = Dijkstra.findShortestPathLength(cityMap, start, end);
//
//                    // Update the submap with the new segment
//                    if (length != Double.MAX_VALUE) {
//                        allSegments.add(new RoadSegment(start, end, length, null));
//                    }
//                } else {
//                    // If a direct segment exists with a finite length, use it
//                    double length = cityMap.getSegmentLength(start, end);
//                    allSegments.add(new RoadSegment(start, end, length, null));
//                }
//            }
//        }
//
//        // Update the submap with all segments
//        CityMap optimalRouteMap = new CityMap(warehouse, allIntersections, allSegments);
//    }
//
//    @Override
//    public String toString() {
//        return "Tour{" +
//                "deliveries=" + deliveries +
//                '}';
//    }
//}
//
///*
//public void calculateOptimalRoute(CityMap cityMap) {
//        List<Intersection> allIntersections = new ArrayList<>();
//        List<RoadSegment> allSegments = new ArrayList<>();
//
//        // Calculate the shortest path for each pair of deliveries
//        for (int i = 0; i < deliveries.size(); i++) {
//            for (int j = i + 1; j < deliveries.size(); j++) {
//                Intersection start = deliveries.get(i).getIntersection();
//                Intersection end = deliveries.get(j).getIntersection();
//
//
//                List<Intersection> shortestPath = Dijkstra.findShortestPath(cityMap, start, end);
//
//                // Add intersections and segments of the shortest path to the lists
//                for (int k = 0; k < shortestPath.size() - 1; k++) {
//                    Intersection src = shortestPath.get(k);
//                    Intersection dest = shortestPath.get(k + 1);
//
//                    allIntersections.add(src);
//                    allIntersections.add(dest);
//
//                    // Assume RoadSegment constructor takes (source, destination, length)
//                    // and that we have a method to get the length between intersections
//                    int length = getLengthBetweenIntersections(cityMap, src, dest);
//                    allSegments.add(new RoadSegment(src, dest, length));
//                }
//            }
//        }
//
//        // Remove duplicates
//        allIntersections = new ArrayList<>(new HashSet<>(allIntersections));
//        allSegments = new ArrayList<>(new HashSet<>(allSegments));
//
//        // Create a new CityMap with these intersections and road segments
//        CityMap optimalRouteMap = new CityMap(allIntersections, allSegments, null);
//    }
//
//Modify this function to create the submap of the map with all the intersections of deviveres and and the segment between them .
//Then check before dijkstra on the parameter graph if length between the 2 intersections is infinite, if it is then we compute length with dijkstra and put this length in the submap !
// */