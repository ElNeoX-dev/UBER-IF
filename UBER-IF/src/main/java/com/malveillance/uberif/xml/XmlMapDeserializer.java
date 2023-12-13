package com.malveillance.uberif.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.malveillance.uberif.model.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlMapDeserializer {

    public List<Object> mapElements = new ArrayList<>();

    private String warehouseId;
    private Warehouse warehouse;
    private List<Intersection> intersectionsElements = new ArrayList<>();
    private List<RoadSegment> segmentElements = new ArrayList<>();


    public XmlMapDeserializer(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Parse Warehouse
            NodeList warehouseList = doc.getElementsByTagName("warehouse");
            for (int i = 0; i < warehouseList.getLength(); i++) {
                Node node = warehouseList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    warehouseId = element.getAttribute("address");
                }
            }

            Map<String, Pair<Tour, Courier>> tours = new HashMap<>();
            NodeList tourList = doc.getElementsByTagName("tour");
            boolean hasTour = tourList.getLength() > 0;

            if (hasTour) {
                // create and add tours
                for (int i = 0; i < tourList.getLength(); i++) {
                    Node tourNode = tourList.item(i);
                    if (tourNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element tourElement = (Element) tourNode;
                        String tourId = tourElement.getAttribute("tourId");
                        Tour tour = new Tour(Integer.parseInt(tourId));

                        String name = tourElement.getAttribute("courier");
                        String color = tourElement.getAttribute("color");
                        Courier courier = new Courier(name, Color.valueOf(color));

                        Pair<Tour, Courier> tourCourierPair = new Pair<>(tour, courier);
                        tours.put(tourId, tourCourierPair);
                    }
                }
            }


            // Parse Intersections
            NodeList intersectionList = doc.getElementsByTagName("intersection");
            for (int i = 0; i < intersectionList.getLength(); i++) {
                Node node = intersectionList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Intersection intersection = new Intersection(
                            element.getAttribute("id"),
                            Double.parseDouble(element.getAttribute("latitude")),
                            Double.parseDouble(element.getAttribute("longitude"))
                    );

                    if (hasTour) {
                        String timeWindowStart = element.getAttribute("timeWindowStart");
                        String timeWindowEnd = element.getAttribute("timeWindowEnd");
                        String tourId = ((Element) element.getParentNode()).getAttribute("tourId");

                        Delivery delivery = new Delivery(intersection, new TimeWindow(Integer.parseInt(timeWindowStart), Integer.parseInt(timeWindowEnd)));
                        tours.get(tourId).getKey().addDelivery(delivery);
                    }

                    mapElements.add(intersection);
                    intersectionsElements.add(intersection);
                    if (element.getAttribute("id").equals(warehouseId)) {
                        mapElements.add(new Warehouse(intersection));
                        warehouse = new Warehouse(intersection);
                    }
                }
            }

            // Parse Segments
            NodeList segmentList = doc.getElementsByTagName("segment");
            for (int i = 0; i < segmentList.getLength(); i++) {
                Node node = segmentList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String origin = element.getAttribute("origin");
                    String destination = element.getAttribute("destination");
                    Intersection originIntersection = null;
                    Intersection destinationIntersection = null;


                    // find intersection in mapElements in order to put Intersection in RoadSegment
                    for (Intersection intersection : intersectionsElements) {
                        if (intersection.getId().equals(origin)) {
                            originIntersection = intersection;
                        } else if (intersection.getId().equals(destination)) {
                            destinationIntersection = intersection;
                        }
                        if (originIntersection != null && destinationIntersection != null) {
                            RoadSegment roadSegment = new RoadSegment(
                                    originIntersection,
                                    destinationIntersection,
                                    Double.parseDouble(element.getAttribute("length")),
                                    element.getAttribute("name")
                            );
                            mapElements.add(roadSegment);
                            segmentElements.add(roadSegment);
                            break;
                        }

                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Warehouse getWarehouse() {
        return warehouse;
    }

    public List<Intersection> getIntersectionsElements() {
        return intersectionsElements;
    }

    public List<RoadSegment> getSegmentElements() {
        return segmentElements;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setIntersectionsElements(List<Intersection> intersectionsElements) {
        this.intersectionsElements = intersectionsElements;
    }

    public void setSegmentElements(List<RoadSegment> segmentElements) {
        this.segmentElements = segmentElements;
    }
}