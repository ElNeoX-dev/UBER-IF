package com.malveillance.uberif.model;

import java.util.Observable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;

public class CityMap  extends Observable {
    private static final double INFINITE_LENGTH = Integer.MAX_VALUE;
    private final Map<Intersection, List<RoadSegment>> nodes;
    private final Warehouse warehouse;

    public CityMap(Warehouse warehouse, List<Intersection> intersections, List<RoadSegment> segments) {
        this.warehouse = warehouse;
        this.nodes = new HashMap<>();

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

    private void addRoadSegment(RoadSegment segment) {
        this.nodes.get(segment.getOrigin()).add(segment);
    }

    public Map<Intersection,Pair<Intersection, Double>> makeCompleteGraph() {
        Map<Intersection,Pair<Intersection, Double>> completeGraph = new HashMap<>();
        for (Intersection source : this.nodes.keySet()) {
            for (Intersection destination : this.nodes.keySet()) {
                if (!source.equals(destination) && !hasRoadSegment(source, destination)) {
                    completeGraph.put(source,new Pair<>(destination,INFINITE_LENGTH));
                } else if (hasRoadSegment(source,destination))
                {
                    completeGraph.put(source,new Pair<>(destination,getDistance(source,destination)));
                }
            }
        }
        return completeGraph;
    }

    public boolean hasRoadSegment(Intersection source, Intersection destination) {
        return this.nodes.get(source).stream()
                .anyMatch(segment -> segment.getDestination().equals(destination));
    }

    public List<RoadSegment> getAdjacentRoads(Intersection intersection) {
        return this.nodes.getOrDefault(intersection, new ArrayList<>());
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
}
