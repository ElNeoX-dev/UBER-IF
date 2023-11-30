package com.malveillance.uberif.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.model.Warehouse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlMapDeserializer {

    // Inutile maintenant mais je laisse au cas où
    public List<Object> mapElements = new ArrayList<>();

    private Warehouse warehouse ;
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
                    mapElements.add(new Warehouse(element.getAttribute("address")));
                    warehouse = new Warehouse(element.getAttribute("address"));
                }
            }

            // Parse Intersections
            NodeList intersectionList = doc.getElementsByTagName("intersection");
            for (int i = 0; i < intersectionList.getLength(); i++) {
                Node node = intersectionList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    mapElements.add(new Intersection(
                            element.getAttribute("id"),
                            Double.parseDouble(element.getAttribute("latitude")),
                            Double.parseDouble(element.getAttribute("longitude"))
                    ));
                    intersectionsElements.add(new Intersection(
                            element.getAttribute("id"),
                            Double.parseDouble(element.getAttribute("latitude")),
                            Double.parseDouble(element.getAttribute("longitude"))
                    ));
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
                    Intersection originIntersection = null ;
                    Intersection destinationIntersection = null ;


                    // find intersection in mapElements in order to put Intersection in RoadSegment
                    for (Intersection intersection : intersectionsElements) {
                            if (intersection.getId().equals(origin)) {
                                originIntersection = intersection;
                            } else  if (intersection.getId().equals(destination)) {
                                destinationIntersection = intersection;
                            }
                            if (originIntersection != null && destinationIntersection != null) {
                                mapElements.add(new RoadSegment(
                                        originIntersection,
                                        destinationIntersection,
                                        Double.parseDouble(element.getAttribute("length")),
                                        element.getAttribute("name")
                                ));
                                segmentElements.add(new RoadSegment(
                                        originIntersection,
                                        destinationIntersection,
                                        Double.parseDouble(element.getAttribute("length")),
                                        element.getAttribute("name")
                                ));
                                break ;
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