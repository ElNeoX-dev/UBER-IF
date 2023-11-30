package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Warehouse;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import com.malveillance.uberif.model.Shape;
import com.malveillance.uberif.model.ShapeVisitor;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class GraphicalView extends ShapeVisitor implements Observer {
    private CityMapController cityMapController;
    private PaneController paneController;

    private int nbCouriers = 1;
    @FXML
    protected void onImportDataBtnClick() {
        System.out.println("Import data click");
    }

    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");
    }

    @FXML
    protected void onSaveBtnClick() {
        System.out.println("Save click");
    }

    @FXML
    protected void onRestoreBtnClick() {
        System.out.println("Restore click");
    }

    @FXML
    protected void onMouseEntered(MouseEvent event){
        if (event.getSource() instanceof Button){
            ((Button) event.getSource()).setCursor(javafx.scene.Cursor.HAND);
        }
    }

    @FXML
    protected void onMouseExited(MouseEvent event){
        if (event.getSource() instanceof Button){
            ((Button) event.getSource()).setCursor(Cursor.DEFAULT);
        }
    }

    @FXML
    protected void onPlusBtnClick() {
        if (nbCouriers == 1) {
            minusBtn.getStyleClass().remove("grey-state");
            minusBtn.getStyleClass().add("blue-state");
        }

        nbCouriers++;
        nbCourierLb.setText(String.valueOf(nbCouriers));
    }

    @FXML
    protected void onMinusBtnClick() {
        if (nbCouriers > 1) {
            nbCouriers--;
            nbCourierLb.setText(String.valueOf(nbCouriers));
        }
        if (nbCouriers == 1) {
            minusBtn.getStyleClass().remove("blue-state");
            minusBtn.getStyleClass().add("grey-state");
        }
    }

    @FXML
    private Label nbCourierLb;

    @FXML
    private Button minusBtn;

    @FXML
    private Pane mapPane;


    @FXML
    private ChoiceBox choiceMap;
    double width ;
    double height ;

    private CityMap cityMap;


    @FXML
    public void initialize() {
        width = mapPane.getWidth();
        height = mapPane.getHeight();

        // Initialize UI
        minusBtn.getStyleClass().add("grey-state");

        // Create a list of choices
        ObservableList<String> mapChoices = FXCollections.observableArrayList("Small Map", "Medium Map", "Large Map");

        // Set the choices in the ChoiceBox
        choiceMap.setItems(mapChoices);

        // Optionally, set a default selected item
        choiceMap.getSelectionModel().selectFirst();

        // Add a listener
        choiceMap.getSelectionModel().selectedItemProperty().addListener(new ChoiceMenuListener(cityMapController, this));

        // Add a listener to the width property of mapPane
        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            update(cityMap, cityMap.getNodes());
        });

        // Add a listener to the height property of mapPane
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            update(cityMap, cityMap.getNodes());
        });


        // Load initial map
        this.cityMap = cityMapController.loadNewCityMap((String) choiceMap.getSelectionModel().getSelectedItem());
        cityMap.addObserver(this);
    }
    
    public GraphicalView() {
        CityMapService cityMapService = new CityMapService();
        PaneService paneService = new PaneService();
        cityMapController = new CityMapController(cityMapService);
        paneController = new PaneController(paneService);
    }

    public GraphicalView(CityMapController cityMapController, PaneController paneController) {
        this.cityMapController = cityMapController;
        this.paneController = paneController;

    }

    // Methods to update the UI
    // These methods can be called by the controller to update the UI
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CityMap newmap) {
            mapPane.getChildren().clear();
            // Draw lines first
            newmap.getWarehouse().accept(this);
            for (Shape elem : newmap.getNodes().keySet()) {
                elem.accept(this);
            }
            for (List<RoadSegment> list : newmap.getNodes().values()) {
                for (Shape elem : list) {
                    elem.accept(this);
                }
            }
        }
    }

    public void visit(Warehouse warehouse) {
        // Implementation to draw warehouse
    }

    public void visit(Intersection intersection) {
        // Implementation to draw intersection
        double intersectionX = paneController.getIntersectionX(intersection, width);
        double intersectionY = paneController.getIntersectionY(intersection, height);
        Circle intersectionDot = new Circle(intersectionX, intersectionY, 3, Color.RED);
        mapPane.getChildren().add(intersectionDot);
    }

    public void visit(RoadSegment segment) {
        // Implementation to draw segment
        Line road = new Line(
                paneController.getIntersectionX(segment.getOrigin(), width),
                paneController.getIntersectionY(segment.getOrigin(), height),
                paneController.getIntersectionX(segment.getDestination(), width),
                paneController.getIntersectionY(segment.getDestination(), height)
        );

        road.setStroke(Color.GREY);
        road.setStrokeWidth(1.0);
        mapPane.getChildren().add(road);
    }

    public CityMap getCityMap() {return this.cityMap; }


}