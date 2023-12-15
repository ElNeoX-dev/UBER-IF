package com.malveillance.uberif.xml;

import com.malveillance.uberif.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class XmlMapDeserializerTest {

    private XmlMapDeserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new XmlMapDeserializer("src/main/resources/com/malveillance/uberif/testMap.xml");
    }

    @Test
    void testWarehouseParsing() {
        Warehouse warehouse = deserializer.getWarehouse();
        assertNotNull(warehouse);
    }

    @Test
    void testIntersectionsParsing() {
        List<Intersection> intersections = deserializer.getIntersectionsElements();
        assertNotNull(intersections);
        assertFalse(intersections.isEmpty());
    }

    @Test
    void testSegmentsParsing() {
        List<RoadSegment> segments = deserializer.getSegmentElements();
        assertNotNull(segments);
        assertFalse(segments.isEmpty());
    }
}
