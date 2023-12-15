package com.malveillance.uberif.xml;

import java.io.File;
import java.util.List;
import java.util.Map;

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
import java.io.IOException;


public class XMLserializer /*implements Visitor */ {// Singleton

    private Document document;

    private Map<Intersection, List<RoadSegment>> segmentUsed;

    private static XMLserializer instance = null;
    private XMLserializer(){}
    public static XMLserializer getInstance(){
        if (instance == null)
            instance = new XMLserializer();
        return instance;
    }


    public Document serialize(List<Tour> tourList, /*NECESSAIRE ??? Map<Intersection, List<RoadSegment>> segmentUsed,*/ Warehouse warehouse, String  name) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
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
        for (Tour tour : tourList) {
            // tour element
            Element tourElement = document.createElement("tour");
            root.appendChild(tourElement);

            createAttribute(tourElement, "tourId", String.valueOf(tour.getId()));

            for (Delivery delivery : tour.getDeliveries()) {
                Intersection intersection = delivery.getIntersection();
                // intersection element
                Element intersectionElement = document.createElement("intersection");
                tourElement.appendChild(intersectionElement);

                createAttribute(intersectionElement, "id", intersection.getId());
                createAttribute(intersectionElement, "latitude", String.valueOf(intersection.getLatitude()));
                createAttribute(intersectionElement, "longitude", String.valueOf(intersection.getLongitude()));
                createAttribute(intersectionElement, "timeWindow", String.valueOf(delivery.getTimeWindow()));
//                On verra plus tard apparemment
//                createAttribute(intersectionElement, "courier", String.valueOf(delivery.getCourier().getName()));
//                createAttribute(intersectionElement, "color", String.valueOf(delivery.getCourier().getColor()));
                createAttribute(intersectionElement, "order", String.valueOf(i));
                i++;
            }
            i = 1 ;
        }

        // save the xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("src/main/resources/output/" + name + ".xml"));
        transformer.transform(domSource, streamResult);


        return null;
    }

    private void createAttribute(Element root, String name, String value){
        Attr attribute = document.createAttribute(name);
        root.setAttributeNode(attribute);
        attribute.setValue(value);
    }

    public void writeDocumentToFile(Document document, String filename) throws TransformerException, IOException {
        File file = new File(filename);
        File parentDirectory = file.getParentFile();

        // Check if the parent directories exist and create them if they don't
        if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
            throw new IOException("Failed to create directory: " + parentDirectory.getAbsolutePath());
        }

        // Now proceed with writing the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }




}
