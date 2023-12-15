package com.malveillance.uberif.model.service;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CityMapServiceTest {

    @Mock
    private XmlMapDeserializer mockXmlMapDeserializer;

    private CityMapService cityMapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityMapService = new CityMapService();
    }
    /*
    @Test
    void testLoadMap() {
        // Arrange
        String mapName = "Test Map";
        String expectedFileName = "testMap.xml"; // Assuming the method constructs the file name like this

        Warehouse mockWarehouse = new Warehouse(); // Assuming a constructor
        // Mock intersections and segments as necessary
        List<Intersection> mockIntersections = ...;
        List<RoadSegment> mockSegments = ...;

        // Mock the behavior of XmlMapDeserializer
        when(mockXmlMapDeserializer.getWarehouse()).thenReturn(mockWarehouse);
        when(mockXmlMapDeserializer.getIntersectionsElements()).thenReturn(Collections.singletonList(mockIntersections));
        when(mockXmlMapDeserializer.getSegmentElements()).thenReturn(Collections.singletonList(mockSegments));

        // You might need to mock the static call to XmlMapDeserializer
        // This requires PowerMockito as Mockito does not support mocking static methods

        // Act
        CityMap result = cityMapService.loadMap(mapName);

        // Assert
        assertEquals(expectedFileName, result.getFileName()); // Assuming CityMap stores the file name
        // Add more assertions to verify the contents of CityMap
    }*/
}
