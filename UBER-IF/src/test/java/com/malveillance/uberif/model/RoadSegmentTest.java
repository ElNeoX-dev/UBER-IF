package com.malveillance.uberif.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoadSegmentTest {
    private RoadSegment roadSegment;
    private Intersection origin;
    private Intersection destination;
    private final double length = 10.0;
    private final String name = "Main Street";

    @BeforeEach
    void setUp() {
        origin = new Intersection("1", 0.0, 0.0);
        destination = new Intersection("2", 1.0, 1.0);
        roadSegment = new RoadSegment(origin, destination, length, name);
    }

    @Test
    void getAndSetDestination_ShouldReturnCorrectValue() {
        assertEquals(destination, roadSegment.getDestination(), "getDestination should return the correct destination.");

        Intersection newDestination = new Intersection("3", 2.0, 2.0);
        roadSegment.setDestination(newDestination);
        assertEquals(newDestination, roadSegment.getDestination(), "setDestination should update the destination.");
    }

    @Test
    void getAndSetLength_ShouldReturnCorrectValue() {
        assertEquals(length, roadSegment.getLength(), "getLength should return the correct length.");

        double newLength = 15.0;
        roadSegment.setLength(newLength);
        assertEquals(newLength, roadSegment.getLength(), "setLength should update the length.");
    }

    @Test
    void getAndSetName_ShouldReturnCorrectValue() {
        assertEquals(name, roadSegment.getName(), "getName should return the correct name.");

        String newName = "Broadway";
        roadSegment.setName(newName);
        assertEquals(newName, roadSegment.getName(), "setName should update the name.");
    }

    @Test
    void getAndSetOrigin_ShouldReturnCorrectValue() {
        assertEquals(origin, roadSegment.getOrigin(), "getOrigin should return the correct origin.");

        Intersection newOrigin = new Intersection("4", 3.0, 3.0);
        roadSegment.setOrigin(newOrigin);
        assertEquals(newOrigin, roadSegment.getOrigin(), "setOrigin should update the origin.");
    }

    @Test
    void toString_ShouldReturnCorrectFormat() {
        String expected = "RoadSegment{" +
                "destination='" + destination + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
        assertEquals(expected, roadSegment.toString(), "toString should return the correct string representation.");
    }

    @Test
    void accept_ShouldInvokeVisitOnShapeVisitor() {
        ShapeVisitor mockVisitor = mock(ShapeVisitor.class);
        roadSegment.accept(mockVisitor);
        verify(mockVisitor).visit(roadSegment);
    }
}
