package com.malveillance.uberif.model;

import com.malveillance.uberif.xml.XmlMapDeserializer;

import java.util.List;

public class CityMap {
    public List<Intersection> intersection;
    public List<RoadSegment> segments;

    public CityMap(String pathName) {
        XmlMapDeserializer parser = new XmlMapDeserializer(pathName);

    }
}
