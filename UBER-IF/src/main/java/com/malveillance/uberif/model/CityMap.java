package com.malveillance.uberif.model;

import javafx.util.Pair;

import java.util.*;

/**
 * The CityMap class represents all the intersections and road segments of a map.
 */
public class CityMap extends Observable {

    /**
     * The maximum length of a road segment.
     */
    public static final double INFINITE_LENGTH = Double.MAX_VALUE;
    /**
     * The list of intersections and road segments of the city map.
     */
    private final Map<Intersection, List<RoadSegment>> nodes;

    /**
     * The list of couriers and their selected intersections with timeWindow.
     */
    private Map<Courier, List<Pair<Intersection, TimeWindow>>> courierDotMap;

    /**
     * The warehouse of the city map.
     */
    private final Warehouse warehouse;

    /**
     * The list of couriers and their travel plans.
     */
    private Map<Courier, List<Pair<Intersection, Date>>> travelList;

    /**
     * The name of the map.
     */
    private String mapName;

    /**
     * Constructor for the CityMap class
     * @param warehouse The warehouse of the city map
     * @param intersections The list of intersections in the city map
     */
    public CityMap(Warehouse warehouse, List<Intersection> intersections) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node, new ArrayList<>());
        }
        this.travelList = new HashMap<>();
    }

    /**
     * Constructor for the CityMap class
     * @param warehouse The warehouse of the city map
     * @param intersections The list of intersections in the city map
     * @param segments The list of road segments in the city map
     */
    public CityMap(Warehouse warehouse, List<Intersection> intersections, List<RoadSegment> segments, String mapName) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.mapName = mapName;
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node, new ArrayList<>());
        }
        for (RoadSegment segment : segments) {
            this.nodes.get(segment.getOrigin()).add(segment);
        }
        this.travelList = new HashMap<>();
    }

    /**
     * Constructor for the CityMap class
     * @param warehouse The warehouse of the city map
     * @param intersections The list of intersections in the city map
     * @param mapName The list of road segments in the city map
     * @param isSavedMap Whether the map is saved or not
     */
    public CityMap(Warehouse warehouse, List<Pair<Tour, Courier>> tourCourierPairList, List<Intersection> intersections, String mapName, boolean isSavedMap) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.mapName = mapName;
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node, new ArrayList<>());
        }

        for (Pair<Tour, Courier> pair : tourCourierPairList) {
            Tour tour = pair.getKey();
            Courier courier = pair.getValue();
            List<Pair<Intersection, TimeWindow>> pairs = new ArrayList<>();
            for (Delivery delivery : tour.getDeliveries()) {
                // if the intersection is not the warehouse
                if (!delivery.getIntersection().getId().equals(warehouse.getIntersection().getId())) {
                    pairs.add(new Pair<>(delivery.getIntersection(), delivery.getTimeWindow()));
                }
            }
            //courier.setCurrentTour(tour);

            this.courierDotMap.put(courier, pairs);
        }
    }

    /**
     * Merge the data of a new CityMap into the current CityMap
     * @param newCityMap The new CityMap to merge
     */
    public void merge(CityMap newCityMap) {
        for (Intersection newIntersection : newCityMap.getNodes().keySet()) {
            Intersection existingIntersection = this.nodes.keySet().stream()
                    .filter(i -> i.getId().equals(newIntersection.getId()))
                    .findFirst()
                    .orElse(newIntersection);

            existingIntersection.setIsOwned(true);
        }

        for (Map.Entry<Courier, List<Pair<Intersection, TimeWindow>>> entry : newCityMap.getCourierDotMap().entrySet()) {
            List<Pair<Intersection, TimeWindow>> updatedPairs = new ArrayList<>();
            for (Pair<Intersection, TimeWindow> pair : entry.getValue()) {
                Intersection updatedIntersection = this.nodes.keySet().stream()
                        .filter(i -> i.getId().equals(pair.getKey().getId()))
                        .findFirst()
                        .orElse(pair.getKey());

                updatedPairs.add(new Pair<>(updatedIntersection, pair.getValue()));
            }

            Courier existingCourier = this.courierDotMap.keySet().stream()
                    .filter(c -> c.getName().equals(entry.getKey().getName()))
                    .findFirst()
                    .orElse(null);

            if (existingCourier != null) {
                List<Pair<Intersection, TimeWindow>> existingCourierData = this.courierDotMap.get(existingCourier);
                existingCourierData.addAll(updatedPairs);
            } else {
                this.courierDotMap.put(entry.getKey(), updatedPairs);
            }
        }
    }

    /**
     * Add an intersection to the city map
     * @param intersection The intersection to add
     */
    private void addIntersection(Intersection intersection) {
        if (!this.nodes.containsKey(intersection)) {
            this.nodes.put(intersection, new ArrayList<>());
        }
    }

    /**
     * Add a courier to the city map
     * @param courier The courier to add
     */
    public void addCourier(Courier courier) {
        this.courierDotMap.put(courier, new ArrayList<>());
    }

    /**
     * Remove a courier from the city map
     * @param courier The courier to remove
     */
    public void removeCourier(Courier courier) {
        this.courierDotMap.remove(courier);
    }

    /**
     * returns the list of selected intersections and timeWindow by the courier
     * @param courier The courier to get the selected intersections from
     * @return The list of selected intersections and timeWindow by the courier
     */
    public List<Pair<Intersection, TimeWindow>> getSelectedPairList(Courier courier) {
        return courierDotMap.get(courier);
    }

    /**
     * returns the list of selected intersections by the courier
     * @param courier The courier to get the selected intersections from
     * @return The list of selected intersections by the courier
     */
    public List<Intersection> seeSelectedIntersectionList(Courier courier) {
        List<Intersection> intersections = new ArrayList<>();

        // Parcourir les paires et extraire les Intersections
        for (Pair<Intersection, TimeWindow> pair : courierDotMap.get(courier)) {
            intersections.add(pair.getKey());
        }

        return intersections;
    }

    /**
     * returns the list of couriers
     *
     * @return The list of couriers
     */
    public List<Courier> getListCourier() {
        List<Courier> couriers = new ArrayList<>();

        for (Map.Entry<Courier, List<Pair<Intersection, TimeWindow>>> entry : courierDotMap.entrySet()) {
            couriers.add(entry.getKey());
        }

        return couriers;
    }

    /**
     * returns the Map of couriers and their selected intersections with timeWindow
     * @return The Map of couriers and their selected intersections with timeWindow
     */
    public Map<Courier, List<Pair<Intersection, TimeWindow>>> getCourierDotMap() {
        return courierDotMap;
    }

    /**
     * Set the courierDotMap
     * @param courierDotMap The courierDotMap to set
     */
    public void setCourierDotMap(Map<Courier, List<Pair<Intersection, TimeWindow>>> courierDotMap) {
        this.courierDotMap = courierDotMap;
    }

    /**
     * Add a travel plan to the travel list
     * @param courier The courier to add the travel plan to
     * @param plan The travel plan to add
     */
    public void addTravelPlan(Courier courier, List<Pair<Intersection, Date>> plan) {
        travelList.put(courier, plan);
    }

    /**
     * Get the travel plan of a courier
     * @param courier The courier to get the travel plan from
     * @return The travel plan of the courier
     */
    public List<Pair<Intersection, Date>> getTravelPlan(Courier courier) {
        return travelList.getOrDefault(courier, new ArrayList<>());
    }

    public void setTravelList(Map<Courier, List<Pair<Intersection, Date>>> travelList) {
        this.travelList = travelList;
    }

    /**
     * Remove the travel plan of a courier
     * @param courier The courier to remove the travel plan from
     */
    public void removeTravelPlan(Courier courier) {
        travelList.remove(courier);
    }

    /**
     * Get the travel list
     * @return The travel list
     */
    public Map<Courier, List<Pair<Intersection, Date>>> getTravelList() {
        return travelList;
    }

    /**
     * add a road segment to the city map
     * @param segment The road segment to add
     */
    public void addRoadSegment(RoadSegment segment) {
        this.nodes.get(segment.getOrigin()).add(segment);
    }

    /**
     * check if the city map has a road segment from source to destination
     * @param source The source intersection
     * @param destination The destination intersection
     * @return Whether the city map has a road segment from source to destination
     */
    public boolean hasRoadSegment(Intersection source, Intersection destination) {
        return this.nodes.get(source).stream()
                .anyMatch(segment -> segment.getDestination().equals(destination));
    }

    /**
     * get the adjacent roads of an intersection
     * @param intersection The intersection to get the adjacent roads from
     * @return The adjacent roads of the intersection
     */
    public List<RoadSegment> getAdjacentRoads(Intersection intersection) {
        return this.nodes.getOrDefault(intersection, new ArrayList<>());
    }

    /**
     * set the adjacent roads of an intersection
     * @param intersection The intersection to set the adjacent roads of
     * @param roadSegments The adjacent roads to set
     */
    public void setAdjacentRoads(Intersection intersection, List<RoadSegment> roadSegments) {
        this.nodes.get(intersection).addAll(roadSegments);
    }

    /**
     * get the nodes of the city map
     * @return The nodes of the city map
     */
    public Map<Intersection, List<RoadSegment>> getNodes() {
        return this.nodes;
    }

    /**
     * get the warehouse of the city map
     * @return The warehouse of the city map
     */
    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    /**
     * get the distance between two intersections
     * @param source The source intersection
     * @param destination The destination intersection
     * @return The distance between the two intersections
     */
    public double getDistance(Intersection source, Intersection destination) {
        for (RoadSegment segment : this.nodes.get(source)) {
            if (segment.getDestination().equals(destination)) {
                return segment.getLength();
            }
        }
        return INFINITE_LENGTH;
    }

    /**
     * set the distance between two intersections
     * @param source The source intersection
     * @param destination The destination intersection
     * @param distance The distance between the two intersections
     */
    public void setDistance(Intersection source, Intersection destination, double distance) {
        for (RoadSegment segment : this.nodes.get(source)) {
            if (segment.getDestination().equals(destination)) {
                segment.setLength(distance);
                return;
            }
        }
    }

    /**
     * get the number of nodes in the city map
     * @return The number of nodes in the city map
     */
    public int getNbNodes() {
        return nodes.size();
    }

    /**
     * get the map name
     * @return The map name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * check if the intersection is in the city map
     * @param intersection The intersection to check
     * @return Whether the intersection is in the city map
     */
    public boolean IntersectionInMap(Intersection intersection) {
        return this.nodes.keySet().contains(intersection);
    }

    /**
     * makes a deep copy of the city map
     * @return A deep copy of the city map
     */
    public CityMap deepCopy() {
        Warehouse copiedWarehouse = new Warehouse(this.warehouse);
        List<Intersection> copiedIntersections = new ArrayList<>(this.nodes.keySet());

        List<RoadSegment> copiedSegments = new ArrayList<>();
        for (List<RoadSegment> segments : this.nodes.values()) {
            copiedSegments.addAll(segments);
        }

        CityMap copiedCityMap = new CityMap(this.warehouse, copiedIntersections, copiedSegments, this.mapName);

        for (Map.Entry<Courier, List<Pair<Intersection, TimeWindow>>> entry : this.courierDotMap.entrySet()) {
            Courier copiedCourier = new Courier(entry.getKey());
            List<Pair<Intersection, TimeWindow>> courierPairs = entry.getValue();
            if (courierPairs != null) {
                List<Pair<Intersection, TimeWindow>> copiedPairs = new ArrayList<>();
                for (Pair<Intersection, TimeWindow> pair : courierPairs) {
                    Intersection copiedIntersection = new Intersection(pair.getKey());
                    TimeWindow copiedTimeWindow = pair.getValue() != null ? new TimeWindow(pair.getValue()) : null;
                    copiedPairs.add(new Pair<>(pair.getKey(), copiedTimeWindow));
                }
                copiedCityMap.getCourierDotMap().put(entry.getKey(), copiedPairs);
            }
        }

        Map<Courier, List<Pair<Intersection, Date>>> copiedTravelList = new HashMap<>();
        for (Map.Entry<Courier, List<Pair<Intersection, Date>>> entry : this.travelList.entrySet()) {
            Courier copiedCourier = new Courier(entry.getKey());
            List<Pair<Intersection, Date>> travelPlan = entry.getValue();
            if (travelPlan != null) {
                List<Pair<Intersection, Date>> copiedPlan = new ArrayList<>();
                for (Pair<Intersection, Date> pair : travelPlan) {
                    Date copiedDate = pair.getValue() != null ? new Date(pair.getValue().getTime()) : null;
                    copiedPlan.add(new Pair<>(pair.getKey(), copiedDate));
                }
                copiedTravelList.put(entry.getKey(), copiedPlan);
            }
        }

        copiedCityMap.setTravelList(copiedTravelList);

        return copiedCityMap;
    }
}
