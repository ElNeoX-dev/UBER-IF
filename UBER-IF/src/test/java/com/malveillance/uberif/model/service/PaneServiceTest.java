package com.malveillance.uberif.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaneServiceTest {

    private PaneService paneService;

    @BeforeEach
    void setUp() {
        paneService = new PaneService();
    }

    @Test
    void testLongitudeToX() {
        // Set range and min/max values
        paneService.setMinX(0);
        paneService.setMaxX(180);
        paneService.setxRange(180); // Assuming xRange is maxX - minX

        double paneWidth = 500; // Arbitrary pane width
        double longitude = 90; // Midpoint longitude

        // Expected X should be at the center of the pane
        double expectedX = 250;
        assertEquals(expectedX, paneService.longitudeToX(longitude, paneWidth));
    }

    @Test
    void testLatitudeToY() {
        // Set range and min/max values
        paneService.setMinY(0);
        paneService.setMaxY(180);
        paneService.setyRange(180); // Assuming yRange is maxY - minY

        double paneHeight = 500; // Arbitrary pane height
        double latitude = 90; // Midpoint latitude

        // Expected Y should be at the center of the pane
        double expectedY = 250;
        assertEquals(expectedY, paneService.latitudeToY(latitude, paneHeight));
    }

    @Test
    void testSettersAndGetters() {
        // Test for minX
        paneService.setMinX(10);
        assertEquals(10, paneService.getMinX());

        // Test for maxX
        paneService.setMaxX(20);
        assertEquals(20, paneService.getMaxX());

        // Test for xRange
        paneService.setxRange(40);
        assertEquals(40, paneService.getxRange());

        // Test for yRange
        paneService.setyRange(40);
        assertEquals(40, paneService.getyRange());
    }
}
