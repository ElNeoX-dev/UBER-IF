package com.malveillance.uberif.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlMapParser {




    public List<Object> parseXmlFile(String filePath) {
        List<Object> mapElements = new ArrayList<>();
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
                }
            }

            // Parse Segments
            NodeList segmentList = doc.getElementsByTagName("segment");
            for (int i = 0; i < segmentList.getLength(); i++) {
                Node node = segmentList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    mapElements.add(new Segment(
                            element.getAttribute("origin"),
                            element.getAttribute("destination"),
                            Double.parseDouble(element.getAttribute("length")),
                            element.getAttribute("name")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapElements;
    }
}