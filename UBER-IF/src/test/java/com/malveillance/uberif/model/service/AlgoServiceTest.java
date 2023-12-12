package com.malveillance.uberif.model.service;

import com.flextrade.jfixture.JFixture;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlgoServiceTest {

    @Mock
    private CityMap cityMap;

    private JFixture fixture;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fixture = new JFixture();
    }

    @Test
    void testCreateSubGraph() {
        // Arrange
        List<Intersection> intersections = new ArrayList<>(fixture.collections().createCollection(Intersection.class));
        //List<Intersection> intersections = fixture.collections().createCollection(Intersection.class);
        Map<Intersection, List<RoadSegment>> expectedAdjacentRoads = fixture.create(Map.class);

        when(cityMap.getNodes()).thenReturn(expectedAdjacentRoads);
        for (Intersection intersection : intersections) {
            when(cityMap.getAdjacentRoads(intersection)).thenReturn(expectedAdjacentRoads.get(intersection));
        }

        // Act
        CityMap subGraph = AlgoService.createSubGraph(cityMap, intersections);

        // Assert
        assertNotNull(subGraph);
        assertEquals(intersections.size(), subGraph.getNodes().size());
        for (Intersection intersection : intersections) {
            assertEquals(expectedAdjacentRoads.get(intersection), subGraph.getAdjacentRoads(intersection));
        }
    }

    @Test
    void testMakeCompleteGraph() {
        // Arrange
        Map<Intersection, List<RoadSegment>> nodes = fixture.create(Map.class);
        when(cityMap.getNodes()).thenReturn(nodes);

        // Act
        CityMap completeGraph = AlgoService.makeCompleteGraph(cityMap);

        // Assert
        assertNotNull(completeGraph);
        for (Intersection source : nodes.keySet()) {
            for (Intersection destination : nodes.keySet()) {
                if (!source.equals(destination)) {
                    assertTrue(completeGraph.hasRoadSegment(source, destination));
                }
            }
        }
    }

}
