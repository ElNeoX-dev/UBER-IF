package com.malveillance.uberif;

import com.malveillance.uberif.controller.HelloController;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.xml.XmlMapDeserializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    // Hashmap of all intersections
    public static Map<String, XmlMapParser.Intersection> intersectionMap = new HashMap<>();

    // Scaling factors
    public static double minX = Double.MAX_VALUE;
    public static double maxX = Double.MIN_VALUE;
    public static double minY = Double.MAX_VALUE;
    public static double maxY = Double.MIN_VALUE;
    public static double xRange = .0;
    public static double yRange = .0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Create Pane to draw map elems on it
        Pane mapPane = new Pane();
        mapPane.setPrefSize(600, 400);


        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("UBER'IF");
        stage.setScene(scene);
        stage.show();

        XmlMapParser parser = new XmlMapParser();
        HelloController controller = fxmlLoader.getController();

        ListView<String> listView = new ListView<>();

        // Parse the XML file and add items to the ListView
        /*
        XmlMapDeserializer parser = new XmlMapDeserializer("src/main/resources/com/malveillance/uberif/smallMap.xml");

        List<Object> mapElements = parser.mapElements ;
        mapElements.forEach(element -> listView.getItems().add(element.toString()));


        // Fill intersectionsMap (hashmap) while parsing
        for (Object elem : mapElements){
            if (elem instanceof XmlMapParser.Intersection inter){
                intersectionMap.put(inter.id, inter);
                minX = Math.min(minX, inter.longitude);
                maxX = Math.max(maxX, inter.longitude);
                minY = Math.min(minY, inter.latitude);
                maxY = Math.max(maxY, inter.latitude);
            }
        }

        // Compute scaling factors
        xRange = maxX - minX;
        yRange = maxY - minY;

        drawElementsOnMap(controller, mapElements);

    }

    // Draw a medium green dot at the position of the warehouse
    private static void drawWarehouse(HelloController controller, XmlMapParser.Warehouse wh){
        // Find intersection corresponding to the warehouse
        XmlMapParser.Intersection intersection = intersectionMap.get(wh.address);

        // If there exists a corresponding intersection
        if (intersection != null){
            double warehouseX = getIntersectionX(controller, intersection.id);
            double warehouseY = getIntersectionY(controller, intersection.id);
            controller.drawElementOnMap("Warehouse", warehouseX, warehouseY);
        }
    }

    // Draw a small red dot at the position of an intersection
    private static void drawIntersection(HelloController controller, XmlMapParser.Intersection intersection){
        double intersectionX = getIntersectionX(controller, intersection.id);
        double intersectionY = getIntersectionY(controller, intersection.id);
        controller.drawElementOnMap("Intersection", intersectionX, intersectionY);
    }

    // Draw a thin grey line fora road -> draw line between origin and destination
    private static void drawSegment(HelloController controller, XmlMapParser.Segment seg){
        Line road = new Line(
                getIntersectionX(controller, seg.origin),
                getIntersectionY(controller, seg.origin),
                getIntersectionX(controller, seg.destination),
                getIntersectionY(controller, seg.destination)
        );

        road.setStroke(Color.GREY);
        road.setStrokeWidth(1.0);
        controller.getMapPane().getChildren().add(road);
    }

    // Get X position of coordinates
    private static double getIntersectionX(HelloController controller, String interId){
        XmlMapParser.Intersection i = intersectionMap.get(interId);

        if (i != null){
            return longitudeToX(i.longitude, controller.getMapPane().getWidth());
        }
        return .0;
    }

    // Get Y position of coordinates
    private static double getIntersectionY(HelloController controller, String interId){
        XmlMapParser.Intersection i = intersectionMap.get(interId);

        if (i != null){
            return latitudeToY(i.latitude, controller.getMapPane().getHeight());
        }
        return .0;
    }

    // Method to map longitude to X position
    private static double longitudeToX(double longitude, double paneWidth){
        //return ((longitude + 180) / 360)  ;
        return ( (longitude - minX) / xRange ) * paneWidth;
    }

    // Method to map latitude to Y position
    private static double latitudeToY(double latitude, double paneHeight){
        //return ((90 - latitude) / 180)  ;
        return ( (latitude - minY) / yRange ) * paneHeight;
    }

    private void drawElementsOnMap(HelloController controller, List<Object> mapElems) {
        // Draw lines first
        for (Object elem : mapElems) {
            if (elem instanceof XmlMapParser.Segment) {
                XmlMapParser.Segment segment = (XmlMapParser.Segment) elem;
                drawSegment(controller, segment);
            }
        }

        for (Object elem : mapElems) {
            if (elem instanceof XmlMapParser.Warehouse) {
                XmlMapParser.Warehouse warehouse = (XmlMapParser.Warehouse) elem;
                drawWarehouse(controller, warehouse);
            } else if (elem instanceof XmlMapParser.Intersection) {
                XmlMapParser.Intersection intersection = (XmlMapParser.Intersection) elem;
                drawIntersection(controller, intersection);
            }
        }
    }

    public static void redrawElementsOnMap(HelloController controller, List<Object> mapElems) {
        // Clear existing elements on the mapPane
        controller.getMapPane().getChildren().clear();

        // Draw lines first
        for (Object elem : mapElems) {
            if (elem instanceof XmlMapParser.Segment) {
                XmlMapParser.Segment segment = (XmlMapParser.Segment) elem;
                drawSegment(controller, segment);
            }
        }

        for (Object elem : mapElems) {
            if (elem instanceof XmlMapParser.Warehouse) {
                XmlMapParser.Warehouse warehouse = (XmlMapParser.Warehouse) elem;
                drawWarehouse(controller, warehouse);
            } else if (elem instanceof XmlMapParser.Intersection) {
                XmlMapParser.Intersection intersection = (XmlMapParser.Intersection) elem;
                drawIntersection(controller, intersection);
            }
        }
    }



    public static void main(String[] args) {
        launch();
    }
}
