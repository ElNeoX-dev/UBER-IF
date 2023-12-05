package com.malveillance.uberif.model;

import java.util.Observable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CityMap  extends Observable {
    public static final double INFINITE_LENGTH = Integer.MAX_VALUE;
    private final Map<Intersection, List<RoadSegment>> nodes;
    private final Map<Courier, List<Intersection>> courierDotMap;
    private final Warehouse warehouse;

    private String mapName ;


    public CityMap(Warehouse warehouse, List<Intersection> intersections) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node,new ArrayList<>());
        }
    }

    public CityMap(Warehouse warehouse, List<Intersection> intersections, List<RoadSegment> segments, String mapName) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();
        this.mapName = mapName;
        this.courierDotMap = new HashMap<>();

        for (Intersection node : intersections) {
            this.nodes.put(node,new ArrayList<>());
        }
        for (RoadSegment segment : segments) {
            this.nodes.get(segment.getOrigin()).add(segment);
        }
    }

    private void addIntersection(Intersection intersection) {
        if (!this.nodes.containsKey(intersection)) {
            this.nodes.put(intersection,new ArrayList<>());
        }
    }

    public void addCourier(Courier courier) {
        this.courierDotMap.put(courier, new ArrayList<>());
    }

    public void removeCourier(Courier courier) {
        this.courierDotMap.remove(courier);
    }

    public List<Intersection> getSelectedIntersectionList(Courier courier) {
        return courierDotMap.get(courier);
    }

    public List<Courier> getListCourier() {
        List<Courier> couriers = new ArrayList<>();

        for (Map.Entry<Courier, List<Intersection>> entry : courierDotMap.entrySet()) {
            couriers.add(entry.getKey());
        }

        return couriers;
    }

    public Map<Courier, List<Intersection>> getCourierDotMap() {
        return courierDotMap;
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

    public void setAdjacentRoads(Intersection intersection,List<RoadSegment> roadSegments) {
        this.nodes.get(intersection).addAll(roadSegments);
    }

    public Map<Intersection, List<RoadSegment>> getNodes() {
        return this.nodes;
    }

    public Warehouse getWarehouse() { return this.warehouse; }

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

    public int getNbNodes(){
        return nodes.size();
    }

    public String getMapName() {
        return mapName;
    }

    public boolean IntersectionInMap(Intersection intersection){
        return this.nodes.keySet().contains(intersection);
    }
}
