package com.malveillance.uberif;

//import com.malveillance.uberif.utils.CustomColors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Locale;

public class HelloController {
    private XmlMapParser parser = new XmlMapParser();
    int nbCouriers = 1;

    @FXML
    private Label nbCourierLb;

    @FXML
    private Button minusBtn;

    @FXML
    private Pane mapPane;

    public Pane getMapPane() {
        return mapPane;
    }

    @FXML
    private ChoiceBox choiceMap;

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

    // Event handler for hovering over an element
    @FXML
    protected void onMouseEntered(MouseEvent event){
        if (event.getSource() instanceof Button){
            ((Button) event.getSource()).setCursor(Cursor.HAND);
        }
    }

    @FXML
    protected void onMouseExited(MouseEvent event){
        if (event.getSource() instanceof Button){
            ((Button) event.getSource()).setCursor(Cursor.DEFAULT);
        }
    }


    public void drawElementOnMap(String elementType, double x, double y) {
        switch (elementType) {
            case "Warehouse":
                drawWarehouse(x, y);
                break;
            case "Intersection":
                drawIntersection(x, y);
                break;
        }
    }

    private void drawWarehouse(double x, double y) {
        Circle whDot = new Circle(x, y, 5, Color.GREEN);
        mapPane.getChildren().add(whDot);
    }

    private void drawIntersection(double x, double y) {
        Circle intersectionDot = new Circle(x, y, 3, Color.RED);
        mapPane.getChildren().add(intersectionDot);
    }

    private void loadMap(String mapName){
        HelloController controller = this;
        ListView<String> listView = new ListView<>();

        // Split the mapName and take the first word
        String[] words = mapName.split("\\s+");
        String fileName = words[0].toLowerCase();

        // Parse the XML file and add items to the ListView
        List<Object> mapElements = parser.parseXmlFile("src/main/resources/com/malveillance/uberif/" + fileName + "Map.xml");
        mapElements.forEach(element -> listView.getItems().add(element.toString()));

        // Clear existing data
        HelloApplication.intersectionMap.clear();
        HelloApplication.minX = Double.MAX_VALUE;
        HelloApplication.maxX = Double.MIN_VALUE;
        HelloApplication.minY = Double.MAX_VALUE;
        HelloApplication.maxY = Double.MIN_VALUE;

        // Fill intersectionsMap (hashmap) while parsing
        for (Object elem : mapElements){
            if (elem instanceof XmlMapParser.Intersection inter){
                HelloApplication.intersectionMap.put(inter.id, inter);
                HelloApplication.minX = Math.min(HelloApplication.minX, inter.longitude);
                HelloApplication.maxX = Math.max(HelloApplication.maxX, inter.longitude);
                HelloApplication.minY = Math.min(HelloApplication.minY, inter.latitude);
                HelloApplication.maxY = Math.max(HelloApplication.maxY, inter.latitude);
            }
        }

        // Compute scaling factors
        HelloApplication.xRange = HelloApplication.maxX - HelloApplication.minX;
        HelloApplication.yRange = HelloApplication.maxY - HelloApplication.minY;

        HelloApplication.redrawElementsOnMap(controller, mapElements);
    }

    private String getCurrentMapName(){
        return ((String) choiceMap.getSelectionModel().getSelectedItem()).toLowerCase().split("\\s+")[0] + "Map";
    }


    public void initialize() {
        minusBtn.getStyleClass().add("grey-state");

        // Create a list of choices
        ObservableList<String> mapChoices
                = FXCollections.observableArrayList("Small Map", "Medium Map", "Large Map");

        // Set the choices in the ChoiceBox
        choiceMap.setItems(mapChoices);

        // Optionally, set a default selected item
        choiceMap.getSelectionModel().selectFirst();

        choiceMap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected item changed from " + oldValue + " to " + newValue);

                // Load corresponding map
                loadMap(newValue);
            }
        });

        // Add a listener to the width property of mapPane
        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            HelloApplication.redrawElementsOnMap(this, parser.parseXmlFile("src/main/resources/com/malveillance/uberif/" + getCurrentMapName() + ".xml"));
        });

        // Add a listener to the height property of mapPane
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            HelloApplication.redrawElementsOnMap(this, parser.parseXmlFile("src/main/resources/com/malveillance/uberif/" + getCurrentMapName() + ".xml"));
        });


        // Load initial map
        loadMap((String) choiceMap.getSelectionModel().getSelectedItem());
    }
}
