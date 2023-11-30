package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.HelloController;
import com.malveillance.uberif.HelloApplication;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.model.service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import java.io.IOException;


public class GraphicalView implements Observer {

    private HelloController controller;
    private Pane mapPane;
    private Service service;
    double width = Pane.getWidth();
    double height = Pane.getHeight();


    public GraphicalView() {
        // Constructor logic if needed
        CityMap map = service.loadMap();
        map.addObserver(this);
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }

    public void initializeUI(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Set the controller in the FXML Loader
        this.controller = fxmlLoader.getController();
        this.controller.setView(this);

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("UBER'IF");
        stage.setScene(scene);
        stage.show();

        // Call any initialization methods in the controller
        controller.initialize();
    }

    // Methods to update the UI
    // These methods can be called by the controller to update the UI
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List<?>) {
            controller.getMapPane().getChildren().clear();

            // Draw lines first
            for (Object elem : arg) {
                elem.accept(this);
            }
        }
    }

    private void visit(Warehouse warehouse) {
        // Implementation to draw warehouse
    }

    private void visit(Intersection intersection) {
        // Implementation to draw intersection
        double intersectionX = service.getIntersectionX(intersection, width);
        double intersectionY = service.getIntersectionY(intersection, height);
        Circle intersectionDot = new Circle(intersectionX, intersectionY, 3, Color.RED);
        mapPane.getChildren().add(intersectionDot);
    }

    private void visit(RoadSegment segment) {
        // Implementation to draw segment
        Line road = new Line(
                service.getIntersectionX(segment.getOrigin(), width),
                service.getIntersectionY(segment.getOrigin(), height),
                service.getIntersectionX(segment.getDestination(), width),
                service.getIntersectionY(segment.getDestination(), height)
        );

        road.setStroke(Color.GREY);
        road.setStrokeWidth(1.0);
        mapPane.getChildren().add(road);
    }

    // Other methods...
}