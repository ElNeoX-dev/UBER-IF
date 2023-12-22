package com.malveillance.uberif.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IntersectionTest {
    private Intersection intersection;
    private final String id = "123";
    private final double latitude = 40.712776;
    private final double longitude = -74.005974;
    private ShapeVisitor mockVisitor;


    @BeforeEach
    void setUp() {
        intersection = new Intersection(id, latitude, longitude);
        mockVisitor = mock(ShapeVisitor.class);
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        assertEquals(id, intersection.getId(), "getId should return the correct id.");
    }

    @Test
    void propertySetters_ShouldSetPropertiesCorrectly() {
        // Set new properties
        String newId = "456";
        double newLatitude = 34.052235;
        double newLongitude = -118.243683;
        intersection.setId(newId);
        intersection.setLatitude(newLatitude);
        intersection.setLongitude(newLongitude);

        // Assert
        assertEquals(newId, intersection.getId(), "setId should set the correct id.");
        assertEquals(newLatitude, intersection.getLatitude(), "setLatitude should set the correct latitude.");
        assertEquals(newLongitude, intersection.getLongitude(), "setLongitude should set the correct longitude.");
    }

    @Test
    void isOwned_ShouldReflectOwnershipStatus() {
        assertFalse(intersection.isOwned(), "isOwned should initially return false.");

        // Change ownership status
        intersection.setIsOwned(true);
        assertTrue(intersection.isOwned(), "setIsOwned should set the ownership status.");
    }

    @Test
    void equals_ShouldCorrectlyCompareIntersections() {
        Intersection sameIntersection = new Intersection(id, latitude, longitude);
        Intersection differentIntersection = new Intersection("999", latitude, longitude);

        assertEquals(intersection, sameIntersection, "equals should return true for intersections with the same id.");
        assertNotEquals(intersection, differentIntersection, "equals should return false for intersections with different ids.");
    }

    @Test
    void hashCode_ShouldReturnIdHashCode() {
        assertEquals(id.hashCode(), intersection.hashCode(), "hashCode should return the hashCode of the id.");
    }

    @Test
    void toString_ShouldReturnFormattedString() {
        String expectedString = "Intersection " + id + " [Lat: " + latitude + ", Long: " + longitude + "]";
        assertEquals(expectedString, intersection.toString(), "toString should return a correctly formatted string.");
    }

    @Test
    void accept_ShouldInvokeVisitOnShapeVisitor() {
        intersection.accept(mockVisitor);

        verify(mockVisitor).visit(intersection);
    }
}
