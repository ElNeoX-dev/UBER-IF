package com.malveillance.uberif.model.service;

import com.flextrade.jfixture.JFixture;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Tour;
import com.malveillance.uberif.model.algo.TSP;

import com.malveillance.uberif.model.Delivery;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.model.RoadSegment;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlgoServiceTest {

    @Mock
    private CityMap cityMap;

    private JFixture fixture;

    @Mock
    private Tour tour;
    @Mock
    private TSP tsp;

    @Mock
    private Map<Intersection, List<RoadSegment>> nodes;
    private static final double COURIER_SPEED = 15 / 3.6; // Assuming this is a known value

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cityMap.getNodes()).thenReturn(nodes);
        fixture = new JFixture();
    }

    /*
    @Test
    void testConvertToTimeGraph() {
        // Mock RoadSegments and Intersections
        RoadSegment roadSegment1 = mock(RoadSegment.class);
        RoadSegment roadSegment2 = mock(RoadSegment.class);
        List<RoadSegment> roadSegments = Arrays.asList(roadSegment1, roadSegment2);

        when(nodes.values()).thenReturn(Collections.singletonList(roadSegments));

        double length1 = 10.0; // 10 meters
        double length2 = 20.0; // 20 meters

        when(roadSegment1.getLength()).thenReturn(length1);
        when(roadSegment2.getLength()).thenReturn(length2);

        List<Pair<Intersection, Date>> result = AlgoService.calculateOptimalRoute(cityMap, tour);

        verify(roadSegment1).setLength(length1 / COURIER_SPEED);
        verify(roadSegment2).setLength(length2 / COURIER_SPEED);
    }*/

    /*
    @Test
    void testCalculateOptimalRoute() {
        // Mock deliveries and their intersections
        Delivery delivery1 = mock(Delivery.class);
        Delivery delivery2 = mock(Delivery.class);
        Intersection intersection1 = mock(Intersection.class);
        Intersection intersection2 = mock(Intersection.class);

        when(delivery1.getIntersection()).thenReturn(intersection1);
        when(delivery2.getIntersection()).thenReturn(intersection2);

        when(tour.getDeliveries()).thenReturn(Arrays.asList(delivery1, delivery2));

        // Mock the TSP algorithm
        when(tsp.searchSolution(anyLong(), any(CityMap.class), any(Tour.class), any(Date.class)))
                .thenReturn(" appropriate return value ");

        List<Pair<Intersection, Date>> result = AlgoService.calculateOptimalRoute(cityMap, tour);

        assertNotNull(result);
    }*/

/*
    @Test
    void testCreateSubGraph() {
        // Arrange
        List<Intersection> intersections = new ArrayList<>(fixture.collections().createCollection(Intersection.class));
        Map<Intersection, List<RoadSegment>> expectedAdjacentRoads = fixture.create(Map.class);

        when(cityMap.getNodes()).thenReturn(expectedAdjacentRoads);
        for (Intersection intersection : intersections) {
            when(cityMap.getAdjacentRoads(intersection)).thenReturn(expectedAdjacentRoads.get(intersection));
        }

        CityMap subGraph = AlgoService.createSubGraph(cityMap, intersections);

        assertNotNull(subGraph);
        assertEquals(intersections.size(), subGraph.getNodes().size());
        for (Intersection intersection : intersections) {
            assertEquals(expectedAdjacentRoads.get(intersection), subGraph.getAdjacentRoads(intersection));
        }
    }
*/

    /*
    @Test
    void testMakeCompleteGraph() {
        Map<Intersection, List<RoadSegment>> nodes = fixture.create(Map.class);
        when(cityMap.getNodes()).thenReturn(nodes);

        CityMap completeGraph = AlgoService.makeCompleteGraph(cityMap);

        assertNotNull(completeGraph);
        for (Intersection source : nodes.keySet()) {
            for (Intersection destination : nodes.keySet()) {
                if (!source.equals(destination)) {
                    assertTrue(completeGraph.hasRoadSegment(source, destination));
                }
            }
        }
    }
    */
}

