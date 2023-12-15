package com.malveillance.uberif.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;


public class CourierTest {

    private Courier courier;

    @BeforeEach
    void setUp() {
        courier = new Courier("John", Color.BLUE);
    }

    @Test
    void gettersAndSetters_ShouldWorkAsExpected() {
        // Test getters and setters
        assertEquals("John", courier.getName());
        assertEquals(Color.BLUE, courier.getColor());
        assertNull(courier.getId()); // id should be null by default
        assertNull(courier.getCurrentTour()); // currentTour should be null by default

        // Update values using setters
        courier.setId(1L);
        courier.setCurrentTour(new Tour());
        courier.setColor(Color.RED);
        courier.setName("Alice");

        // Verify that getters return the updated values
        assertEquals(1L, courier.getId());
        assertEquals("Alice", courier.getName());
        assertEquals(Color.RED, courier.getColor());
        assertEquals(new Tour(), courier.getCurrentTour());
    }

    @Test
    void selectedIntersectionList_ShouldBeModifiable() {
        // Test selectedIntersectionList
        List<Intersection> selectedIntersectionList = new ArrayList<>();
        selectedIntersectionList.add(new Intersection("A", 0.0, 0.0));
        selectedIntersectionList.add(new Intersection("B", 1.0, 1.0));

        courier.getSelectedIntersectionList().addAll(selectedIntersectionList);

        // Verify that the selectedIntersectionList has been modified
        assertEquals(selectedIntersectionList, courier.getSelectedIntersectionList());
    }

    @Test
    void toString_ShouldReturnFormattedString() {
        // Verify that toString returns the expected formatted string
        assertEquals("Courier{id=null, currentTour=null}", courier.toString());
    }
}

