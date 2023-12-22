package com.malveillance.uberif.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryTest {

    @Test
    void gettersAndSetters_ShouldWorkAsExpected() {
        // Create Intersection and TimeWindow for testing
        Intersection intersection = new Intersection("1", 0.0, 0.0);
        TimeWindow timeWindow = new TimeWindow(8, 0);

        // Create Delivery object
        Delivery delivery = new Delivery(intersection, timeWindow);

        // Test getters and setters
        assertEquals(intersection, delivery.getIntersection());
        assertEquals(timeWindow, delivery.getTimeWindow());

        // Update intersection and timeWindow
        Intersection newIntersection = new Intersection("2", 1.0, 1.0);
        TimeWindow newTimeWindow = new TimeWindow(10, 0);

        delivery.setIntersection(newIntersection);
        delivery.setTimeWindow(newTimeWindow);

        // Verify getters
        assertEquals(newIntersection, delivery.getIntersection());
        assertEquals(newTimeWindow, delivery.getTimeWindow());
    }

    @Test
    void toString_ShouldReturnFormattedString() {
        // Create Intersection and TimeWindow for testing
        Intersection intersection = new Intersection("1", 0.0, 0.0);
        TimeWindow timeWindow = new TimeWindow(8, 0);

        // Create Delivery object
        Delivery delivery = new Delivery(intersection, timeWindow);

        // Verify toString
        assertEquals("Delivery{intersection=Intersection 1 [Lat: 0.0, Long: 0.0], timeWindow=TimeWindow{startingTime=Fri Dec 15 08:00:00 CET 2023, endingTime=Fri Dec 15 08:00:00 CET 2023}}", delivery.toString());
    }
}
