package com.malveillance.uberif.xml;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.malveillance.uberif.model.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLserializer /*implements Visitor */ {// Singleton

    private Document document;

    private Map<Intersection, List<RoadSegment>> segmentUsed;

    public XMLserializer(){}


    public void serialize(Set<Courier> couriersList, /*NECESSAIRE ??? Map<Intersection, List<RoadSegment>> segmentUsed,*/ Warehouse warehouse, String  name) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("map");
        document.appendChild(root);

        // warehouse element
        Element warehouseElement = document.createElement("warehouse");
        root.appendChild(warehouseElement);

        // set an attribute to warehouse element
        Attr warehouseAttr = document.createAttribute("address");
        warehouseAttr.setValue(warehouse.getIntersection().getId());
        warehouseElement.setAttributeNode(warehouseAttr);

        int i = 1 ;
        for (Courier courier : couriersList) {
            if (courier.getCurrentTour() == null) {
                continue;
            }
            Tour tour = courier.getCurrentTour();
            // tour element
            Element tourElement = document.createElement("tour");
            createAttribute(tourElement, "tourId", String.valueOf(tour.getId()));
            createAttribute(tourElement, "courier", String.valueOf(courier.getName()));
            createAttribute(tourElement, "color", String.valueOf(courier.getColor().toString()));
            root.appendChild(tourElement);



            for (Delivery delivery : tour.getDeliveries()) {
                Intersection intersection = delivery.getIntersection();
                // intersection element
                Element intersectionElement = document.createElement("intersection");
                root.appendChild(intersectionElement);

                createAttribute(intersectionElement, "id", intersection.getId());
                createAttribute(intersectionElement, "latitude", String.valueOf(intersection.getLatitude()));
                createAttribute(intersectionElement, "longitude", String.valueOf(intersection.getLongitude()));

                Element deliveryElement = document.createElement("delivery");
                tourElement.appendChild(deliveryElement);

                createAttribute(deliveryElement, "intersectionId", intersection.getId());

                Date startingTime = delivery.getTimeWindow().getStartingTime();
                LocalDateTime startingTimeLDT = LocalDateTime.ofInstant(startingTime.toInstant(), ZoneId.systemDefault());
                createAttribute(deliveryElement, "timeWindowStart", String.valueOf(startingTimeLDT.getHour()));

                Date endingTime = delivery.getTimeWindow().getEndingTime();
                LocalDateTime endingTimeLDT = LocalDateTime.ofInstant(endingTime.toInstant(), ZoneId.systemDefault());
                createAttribute(deliveryElement, "timeWindowEnd", String.valueOf(endingTimeLDT.getHour()));

                createAttribute(deliveryElement, "order", String.valueOf(i));
                i++;
            }
            i = 1 ;
        }

        File outputFile = new File( name + ".uberif");
        outputFile.getParentFile().mkdirs();new StreamResult(outputFile);

        // save the xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(outputFile);
        transformer.transform(domSource, streamResult);



    }

    private void createAttribute(Element root, String name, String value){
        Attr attribute = document.createAttribute(name);
        root.setAttributeNode(attribute);
        attribute.setValue(value);
    }



}
