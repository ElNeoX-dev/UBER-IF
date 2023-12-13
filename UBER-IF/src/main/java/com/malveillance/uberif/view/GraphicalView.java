package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.*;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.xml.XMLserializer;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.net.URL;
import java.util.*;

public class GraphicalView extends ShapeVisitor implements Observer {
    private CityMapController cityMapController;
    private PaneController paneController;

    private Context context;
    private Invoker invoker;

    private XMLserializer xmlSerializer;

    private int nbCouriers = 0;

    private Courier noOne = new Courier("", Color.RED);


    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");

        // Create and execute the OptimizeRouteCommand
        OptimizeRouteCommand optimizeCommand;
        optimizeCommand = new OptimizeRouteCommand(cityMap);
        optimizeCommand.execute();
        //this.cityMap = optimizeCommand.getCityMap();
        for(Courier courier : cityMap.getTravelList().keySet()) {
            for (Pair<Intersection, Date> p : cityMap.getTravelPlan(courier)) {
                if (!(p.getKey() == cityMap.getWarehouse().getIntersection()) || cityMap.getTravelPlan(courier).indexOf(p) == 0) {
                    List<RoadSegment> roadSegments = cityMap.getNodes().get(p.getKey());
                    for (RoadSegment r : roadSegments) {
                        if (r.getDestination() == cityMap.getTravelPlan(courier).get(cityMap.getTravelPlan(courier).indexOf(p) + 1).getKey()) {
                            drawLine(r, courier.getColor());
                            break;
                        }
                    }
                }
            }
        }
        invoker.setCommand(optimizeCommand);
        invoker.executeCommand();
    }

    @FXML
    protected void onSaveBtnClick() {
        System.out.println("Save click");
        String nameOutput = showDialogBoxInput("Enter the output file name", "Output file's name", "Enter the output file's name : ");

        if (!nameOutput.isEmpty()) {
            try {
                xmlSerializer.serialize(cityMap.getCourierDotMap().keySet(), cityMap.getWarehouse(), nameOutput);
            } catch (ParserConfigurationException e) {
                System.out.println("ParserConfigurationException : " + e);
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                System.out.println("TransformerException : " + e);
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    protected void onRestoreBtnClick() {
        System.out.println("Restore click");
        String nameInput = showDialogBoxInput("Enter the input file name", "Input file's name", "Enter the input file's name : ");
        System.out.println(doesResourceExist(nameInput+".uberif.xml"));
        if (!nameInput.isEmpty() && doesResourceExist(nameInput+".uberif.xml")) {
            
            /*try {
                CityMap newMap = xmlSerializer.deserialize(nameInput);
                newMap.addObserver(this);
                cityMapController.loadNewCityMap(newMap);
            } catch (ParserConfigurationException e) {
                System.out.println("ParserConfigurationException : " + e);
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                System.out.println("TransformerException : " + e);
                throw new RuntimeException(e);
            }*/
        }
    }

    @FXML
    protected void onMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setCursor(javafx.scene.Cursor.HAND);
        }
    }

    @FXML
    protected void onMouseExited(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setCursor(Cursor.DEFAULT);
        }
    }

    @FXML
    protected void onPlusBtnClick() {
        String nameCourier = showDialogBoxInput("Enter the courier's name", "Courier's name", "Enter the courier's name : ");

        if (nameCourier != null && !nameCourier.isEmpty()) {
            Random randomInt = new Random();
            Courier courier = new Courier(nameCourier,
                    Color.rgb(randomInt.nextInt(255), randomInt.nextInt(255), randomInt.nextInt(255)));
            cityMap.addCourier(courier);
            ;

            if (nbCouriers == 0) {
                minusBtn.getStyleClass().remove("grey-state");
                minusBtn.getStyleClass().add("blue-state");
            }

            nbCouriers++;
            nbCourierLb.setText(String.valueOf(nbCouriers));
            choiceCourier.getItems().add(courier.getName());
        }
    }

    public String showDialogBoxInput(String title, String header, String content) {
        final String[] res = { null };
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait().ifPresent(result -> {
            res[0] = result;
        });
        return res[0];
    }

    @FXML
    protected void onMinusBtnClick() {
        if (nbCouriers > 0) {
            nbCouriers--;
            nbCourierLb.setText(String.valueOf(nbCouriers));

            Courier lastCourier = cityMap.getListCourier().get(cityMap.getListCourier().size() - 1);

            // clear the dots of the courier
            for (Pair<Intersection, TimeWindow> intersectionPair : cityMap.getSelectedPairList(lastCourier)) {
                intersectionPair.getKey().setFill(Color.RED);
                intersectionPair.getKey().setRadius((height / 220) * coef);

                // cityMap.getCourierDotMap().get(noOne).add(intersectionPair);
            }
            cityMap.removeCourier(lastCourier);

            // If the last is selected
            if (choiceCourier.getSelectionModel().isSelected(choiceCourier.getItems().size() - 1)) {
                choiceCourier.getSelectionModel().selectFirst();
                selectedCourier = new Pair<>(noOne, cityMap.getCourierDotMap().get(noOne));
            }
            choiceCourier.getItems().remove(choiceCourier.getItems().size() - 1);
        }
        if (nbCouriers == 0) {
            minusBtn.getStyleClass().remove("blue-state");
            minusBtn.getStyleClass().add("grey-state");
        }
    }

    @FXML
    private TextField searchBox;

    @FXML
    protected void onSearchBoxTyped() {

        if (searchBox.getText().isEmpty()) {
            for (Intersection intersection : cityMap.getNodes().keySet()) {
                if (intersection.isOwned()) {
                    intersection.getCircle().setRadius((height / 150) * coef);
                } else {
                    intersection.getCircle().setRadius((height / 220) * coef);
                }
                intersection.getCircle().setVisible(true);
            }
        } else {
            for (Intersection intersection : cityMap.getNodes().keySet()) {
                if (intersection.getId().contains(searchBox.getText())) {
                    intersection.getCircle().setRadius((height / 100) * coef);
                    intersection.getCircle().setVisible(true);
                } else {
                    intersection.getCircle().setRadius((height / 220) * coef);
                    intersection.getCircle().setVisible(false);
                }
            }
        }

    }

    @FXML
    private Label nbCourierLb;

    @FXML
    private Label lbInfos;

    @FXML
    private Button minusBtn;

    @FXML
    private Pane mapPane;

    @FXML
    private ChoiceBox choiceMap;

    @FXML
    private ChoiceBox choiceCourier;

    double width;
    double height;

    double coef = 1.0;

    private CityMap cityMap;

    private Pair<Courier, List<Pair<Intersection, TimeWindow>>> selectedCourier;

    @FXML
    public void initialize() {

        this.xmlSerializer = new XMLserializer();

        // Initialize UI
        minusBtn.getStyleClass().add("grey-state");

        // Create a list of choices
        ObservableList<String> mapChoices = FXCollections.observableArrayList("Small Map", "Medium Map", "Large Map");

        // Set the choices in the ChoiceBox
        choiceMap.setItems(mapChoices);

        // Optionally, set a default selected item
        choiceMap.getSelectionModel().selectFirst();

        // Add a listener
        choiceMap.getSelectionModel().selectedItemProperty()
                .addListener(new ChoiceMenuMapListener(cityMapController, this, paneController));
        choiceCourier.getSelectionModel().selectedItemProperty().addListener(new ChoiceMenuCourierListener(this));

        // Load initial map
        this.cityMap = cityMapController.loadNewCityMap((String) choiceMap.getSelectionModel().getSelectedItem());
        this.cityMap.addObserver(this);

        // Add a listener to the width property of mapPane
        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            update(this.cityMap, this.cityMap.getNodes());
        });

        // Add a listener to the height property of mapPane
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            update(this.cityMap, this.cityMap.getNodes());
        });

        cityMap.addCourier(noOne);
        selectedCourier = new Pair<>(noOne, cityMap.getCourierDotMap().get(noOne));

        mapPane.addEventHandler(MouseEvent.MOUSE_MOVED, new IntersectionHoverHandler(this, lbInfos));

        this.context = new Context(this.cityMap); // Assuming you have a default state
        this.invoker = new Invoker();
    }

    public void setCityMapController(CityMapController cityMapController) {
        this.cityMapController = cityMapController;
    }

    public void setPaneController(PaneController paneController) {
        this.paneController = paneController;
    }

    public GraphicalView() {
        CityMapService cityMapService = new CityMapService();
        PaneService paneService = new PaneService();
        cityMapController = new CityMapController(cityMapService);
        paneController = new PaneController(paneService);
    }

    // Methods to update the UI
    // These methods can be called by the controller to update the UI
    @Override
    public void update(Observable o, Object arg) {
        if (mapPane.getWidth() > mapPane.getHeight()) {
            width = mapPane.getHeight();
            height = width;
        } else {
            width = mapPane.getWidth();
            height = width;
        }

        if (o instanceof CityMap newmap) {
            switch (newmap.getMapName()) {
                case "Small" -> coef = 1.0;
                case "Medium" -> coef = 0.75;
                case "Large" -> coef = 0.3;
            }

            newmap.setCourierDotMap(cityMap.getCourierDotMap());
            mapPane.getChildren().clear();
            // Draw lines first
            newmap.getWarehouse().accept(this);
            // newmap.addCourier(noOne);

            for (Shape elem : newmap.getNodes().keySet()) {
                elem.accept(this);
            }

            this.cityMap = newmap;

            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image("file:src/main/resources/com/malveillance/uberif/" + cityMap.getMapName() + "Map.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(width, height, false, false, false, false));

            Background background = new Background(backgroundImage);
            mapPane.setBackground(background);

            // printMinMaxLatLong();

            paneController.updateScale(newmap.getNodes().keySet());
        }

    }

    public void visit(Warehouse warehouse) {
        // Implementation to draw warehouse
        double intersectionX = paneController.getIntersectionX(warehouse.intersection, width);
        double intersectionY = paneController.getIntersectionY(warehouse.intersection, height);
        intersectionX = intersectionX - (width - mapPane.getWidth()) / 2;
        intersectionY = intersectionY - (height - mapPane.getHeight()) / 2;
        warehouse.getIntersection().setCircle(intersectionX, intersectionY, (height / 150) * coef, Color.GREEN);
        mapPane.getChildren().add(warehouse.getIntersection().getCircle());
    }

    public void visit(Intersection intersection) {
        // Implementation to draw intersection
        double intersectionX = paneController.getIntersectionX(intersection, width);
        double intersectionY = paneController.getIntersectionY(intersection, height);
        intersectionX = intersectionX - (width - mapPane.getWidth()) / 2;
        intersectionY = intersectionY - (height - mapPane.getHeight()) / 2;

        if ((intersectionX != paneController.getIntersectionX(cityMap.getWarehouse().intersection, width))
                && (intersectionY != paneController.getIntersectionY(cityMap.getWarehouse().intersection, height))) {

            intersection.setCircle(intersectionX, intersectionY, (height / 220) * coef, Color.RED);

            Set<Courier> couriers = cityMap.getCourierDotMap().keySet();
            for (Courier courier : couriers) {
                if (!courier.getName().isEmpty()) {
                    List<Pair<Intersection, TimeWindow>> pairs = cityMap.getSelectedPairList(courier);
                    for (Pair<Intersection, TimeWindow> pair : pairs) {
                        if (pair.getKey().equals(intersection)) {
                            intersection.setCircle(intersectionX, intersectionY, (height / 150) * coef,
                                    courier.getColor());
                            break;
                        }
                    }
                }
            }

            cityMap.getSelectedPairList(noOne).add(new Pair<>(intersection, new TimeWindow(null, null)));
            mapPane.getChildren().add(intersection.getCircle());
            intersection.getCircle().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new IntersectionClickHandler(intersection, this));

        }
    }

    public void visit(RoadSegment segment) {
        // Implementation to draw segment
        drawLine(segment, Color.GREY);
    }

    public void drawLine(RoadSegment segment, Color color) {
        Line road = new Line(
                paneController.getIntersectionX(segment.getOrigin(), width) - (width - mapPane.getWidth()) / 2,
                paneController.getIntersectionY(segment.getOrigin(), height) - (height - mapPane.getHeight()) / 2,
                paneController.getIntersectionX(segment.getDestination(), width) - (width - mapPane.getWidth()) / 2,
                paneController.getIntersectionY(segment.getDestination(), height) - (height - mapPane.getHeight()) / 2
        );

        road.setStroke(color);
        road.setStrokeWidth(3.0);
        mapPane.getChildren().add(road);
    }

    public CityMap getCityMap() {
        return this.cityMap;
    }

    public void setSelectedCourier(Pair<Courier, List<Pair<Intersection, TimeWindow>>> selectedCourier) {
        this.selectedCourier = selectedCourier;
    }

    public Pair<Courier, List<Pair<Intersection, TimeWindow>>> getSelectedCourier() {
        return selectedCourier;
    }

    public boolean doesResourceExist(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("output/"+resourceName);
        return resource != null;
    }

    /*
     * public void printMinMaxLatLong() {
     * double minLat = Double.MAX_VALUE;
     * double maxLat = Double.MIN_VALUE;
     * double minLong = Double.MAX_VALUE;
     * double maxLong = Double.MIN_VALUE;
     * 
     * 
     * for (Intersection inter : this.cityMap.getNodes().keySet()) {
     * if (inter.getLatitude() < minLat) {
     * minLat = inter.getLatitude();
     * } else if (inter.getLatitude() > maxLat) {
     * maxLat = inter.getLatitude();
     * }
     * 
     * if (inter.getLongitude() < minLong) {
     * minLong = inter.getLongitude();
     * } else if (inter.getLongitude() > maxLong) {
     * maxLong = inter.getLongitude();
     * }
     * }
     * 
     * System.out.println("minLat : " + minLat + "\nmaxLat : " + maxLat +
     * "\nminLong : " + minLong + "\nmaxLong : " + maxLong);
     * }
     */

}