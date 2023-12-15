
package com.malveillance.uberif.model;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CityMapTest {

    private CityMap cityMap;
    private Warehouse warehouse;
    private List<Intersection> intersections;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse(new Intersection("0", 0, 0));
        intersections = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            intersections.add(new Intersection(String.valueOf(i), i, i));
        }
        cityMap = new CityMap(warehouse, intersections);
    }

    @Test
    void testAddCourier() {
        Courier courier = new Courier("Courier 1", Color.BLUE);
        cityMap.addCourier(courier);

        List<Courier> couriers = cityMap.getListCourier();
        assertEquals(1, couriers.size());
        assertTrue(couriers.contains(courier));
    }

    @Test
    void testRemoveCourier() {
        Courier courier = new Courier("Courier 1", Color.BLUE);
        cityMap.addCourier(courier);

        cityMap.removeCourier(courier);

        List<Courier> couriers = cityMap.getListCourier();
        assertEquals(0, couriers.size());
        assertFalse(couriers.contains(courier));
    }

    @Test
    void testAddRoadSegment() {
        Intersection source = intersections.get(0);
        Intersection destination = intersections.get(1);
        RoadSegment roadSegment = new RoadSegment(source, destination, 10, "Main Street");

        cityMap.addRoadSegment(roadSegment);

        assertTrue(cityMap.hasRoadSegment(source, destination));
        assertFalse(cityMap.hasRoadSegment(source, intersections.get(2)));
    }

    @Test
    void testGetAdjacentRoads() {
        Intersection source = intersections.get(0);
        Intersection destination1 = intersections.get(1);
        Intersection destination2 = intersections.get(2);
        RoadSegment roadSegment1 = new RoadSegment(source, destination1, 10, "Main Street");
        RoadSegment roadSegment2 = new RoadSegment(source, destination2, 20, "Broadway");

        cityMap.addRoadSegment(roadSegment1);
        cityMap.addRoadSegment(roadSegment2);

        List<RoadSegment> adjacentRoads = cityMap.getAdjacentRoads(source);
        assertEquals(2, adjacentRoads.size());
        assertTrue(adjacentRoads.contains(roadSegment1));
        assertTrue(adjacentRoads.contains(roadSegment2));
    }

    @Test
    void testGetDistance() {
        Intersection source = intersections.get(0);
        Intersection destination = intersections.get(1);
        RoadSegment roadSegment = new RoadSegment(source, destination, 15, "Main Street");

        cityMap.addRoadSegment(roadSegment);

        double distance = cityMap.getDistance(source, destination);
        assertEquals(15, distance);
    }

    @Test
    void testIntersectionInMap() {
        Intersection intersection = intersections.get(0);
        assertTrue(cityMap.IntersectionInMap(intersection));

        Intersection notInMap = new Intersection("6", 10, 10); // A new intersection not in the map
        assertFalse(cityMap.IntersectionInMap(notInMap));
    }
}
