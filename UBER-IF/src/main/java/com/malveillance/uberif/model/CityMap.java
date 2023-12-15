package com.malveillance.uberif.model;

import javafx.util.Pair;

import java.util.*;

public class CityMap extends Observable {
    public static final double INFINITE_LENGTH = Double.MAX_VALUE;
    private final Map<Intersection, List<RoadSegment>> nodes;
    private Map<Courier, List<Pair<Intersection, TimeWindow>>> courierDotMap;
    private final Warehouse warehouse;
    private Map<Courier, List<Pair<Intersection, Date>>> travelList;

    private String mapName;

    public CityMap(Warehouse warehouse, List<Intersection> intersections) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node, new ArrayList<>());
        }
        this.travelList = new HashMap<>();
    }

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

    private void addIntersection(Intersection intersection) {
        if (!this.nodes.containsKey(intersection)) {
            this.nodes.put(intersection, new ArrayList<>());
        }
    }

    public void addCourier(Courier courier) {
        this.courierDotMap.put(courier, new ArrayList<>());
    }

    public void removeCourier(Courier courier) {
        this.courierDotMap.remove(courier);
    }

    public List<Pair<Intersection, TimeWindow>> getSelectedPairList(Courier courier) {
        return courierDotMap.get(courier);
    }

    public List<Intersection> seeSelectedIntersectionList(Courier courier) {
        List<Intersection> intersections = new ArrayList<>();

        // Parcourir les paires et extraire les Intersections
        for (Pair<Intersection, TimeWindow> pair : courierDotMap.get(courier)) {
            intersections.add(pair.getKey());
        }

        return intersections;
    }

    public List<Courier> getListCourier() {
        List<Courier> couriers = new ArrayList<>();

        for (Map.Entry<Courier, List<Pair<Intersection, TimeWindow>>> entry : courierDotMap.entrySet()) {
            couriers.add(entry.getKey());
        }

        return couriers;
    }

    public Map<Courier, List<Pair<Intersection, TimeWindow>>> getCourierDotMap() {
        return courierDotMap;
    }

    public void setCourierDotMap(Map<Courier, List<Pair<Intersection, TimeWindow>>> courierDotMap) {
        this.courierDotMap = courierDotMap;
    }

    public void addTravelPlan(Courier courier, List<Pair<Intersection, Date>> plan) {
        travelList.put(courier, plan);
    }

    public List<Pair<Intersection, Date>> getTravelPlan(Courier courier) {
        return travelList.getOrDefault(courier, new ArrayList<>());
    }

    public void setTravelList(Map<Courier, List<Pair<Intersection, Date>>> travelList) {
        this.travelList = travelList;
    }

    public void removeTravelPlan(Courier courier) {
        travelList.remove(courier);
    }

    public Map<Courier, List<Pair<Intersection, Date>>> getTravelList() {
        return travelList;
    }

    public void addRoadSegment(RoadSegment segment) {
        this.nodes.get(segment.getOrigin()).add(segment);
    }

    public boolean hasRoadSegment(Intersection source, Intersection destination) {
        return this.nodes.get(source).stream()
                .anyMatch(segment -> segment.getDestination().equals(destination));
    }

    public List<RoadSegment> getAdjacentRoads(Intersection intersection) {
        return this.nodes.getOrDefault(intersection, new ArrayList<>());
    }

    public void setAdjacentRoads(Intersection intersection, List<RoadSegment> roadSegments) {
        this.nodes.get(intersection).addAll(roadSegments);
    }

    public Map<Intersection, List<RoadSegment>> getNodes() {
        return this.nodes;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public double getDistance(Intersection source, Intersection destination) {
        for (RoadSegment segment : this.nodes.get(source)) {
            if (segment.getDestination().equals(destination)) {
                return segment.getLength();
            }
        }
        return INFINITE_LENGTH;
    }

    public void setDistance(Intersection source, Intersection destination, double distance) {
        for (RoadSegment segment : this.nodes.get(source)) {
            if (segment.getDestination().equals(destination)) {
                segment.setLength(distance);
                return;
            }
        }
    }

    public int getNbNodes() {
        return nodes.size();
    }

    public String getMapName() {
        return mapName;
    }

    public boolean IntersectionInMap(Intersection intersection) {
        return this.nodes.keySet().contains(intersection);
    }

    public CityMap deepCopy() {
        Warehouse copiedWarehouse = new Warehouse(this.warehouse);
        List<Intersection> copiedIntersections = new ArrayList<>();
        for (Intersection intersection : this.nodes.keySet()) {
            copiedIntersections.add(new Intersection(intersection));
        }

        List<RoadSegment> copiedSegments = new ArrayList<>();
        for (List<RoadSegment> segments : this.nodes.values()) {
            for (RoadSegment segment : segments) {
                copiedSegments.add(new RoadSegment(segment));
            }
        }

        CityMap copiedCityMap = new CityMap(copiedWarehouse, copiedIntersections, copiedSegments, this.mapName);

        for (Map.Entry<Courier, List<Pair<Intersection, TimeWindow>>> entry : this.courierDotMap.entrySet()) {
            Courier copiedCourier = new Courier(entry.getKey());
            List<Pair<Intersection, TimeWindow>> copiedPairs = new ArrayList<>();
            for (Pair<Intersection, TimeWindow> pair : entry.getValue()) {
                Intersection copiedIntersection = new Intersection(pair.getKey());
                TimeWindow copiedTimeWindow = new TimeWindow(pair.getValue());
                copiedPairs.add(new Pair<>(copiedIntersection, copiedTimeWindow));
            }
            copiedCityMap.getCourierDotMap().put(entry.getKey(), copiedPairs);
        }

        Map<Courier, List<Pair<Intersection, Date>>> copiedTravelList = new HashMap<>();
        for (Map.Entry<Courier, List<Pair<Intersection, Date>>> entry : this.travelList.entrySet()) {
            Courier copiedCourier = new Courier(entry.getKey()); // Assuming there's a copy constructor in Courier
            List<Pair<Intersection, Date>> copiedPlan = new ArrayList<>();
            for (Pair<Intersection, Date> pair : entry.getValue()) {
                Intersection copiedIntersection = new Intersection(pair.getKey());
                if (pair.getValue() != null) {
                    Date copiedDate = new Date(pair.getValue().getTime());
                    copiedPlan.add(new Pair<>(copiedIntersection, copiedDate));
                }
                else{
                    copiedPlan.add(new Pair<>(copiedIntersection, null));
                }
            }
            copiedTravelList.put(copiedCourier, copiedPlan);
        }

        copiedCityMap.setTravelList(copiedTravelList);

        return copiedCityMap;
    }
}
