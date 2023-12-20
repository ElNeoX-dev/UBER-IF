package com.malveillance.uberif.view;

import com.malveillance.uberif.formatters.PDFRoadMap;
import com.malveillance.uberif.controller.*;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.xml.XMLserializer;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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


    public List<Pair<Courier, List<Pair<RoadSegment, Date>>>> courierTourDatas;

    private int nbCouriers = 0;

    private Courier noOne = new Courier("", Color.RED);


    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");
        // Create and execute the OptimizeRouteCommand
        OptimizeRouteCommand optimizeCommand;
        optimizeCommand = new OptimizeRouteCommand(this, context);
        invoker.setCommand(optimizeCommand);
        invoker.executeCommand();
        //optimizeCommand.execute();
        //this.cityMap = optimizeCommand.getCityMap();
        update(this.cityMap, this.cityMap.getNodes());
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
        } else {
            showDialogWarningError("Error", "The file name is empty", "");
        }

    }

    @FXML
    protected void onRestoreBtnClick() {
        System.out.println("Restore click");
        String nameInput = showDialogBoxInput("Enter the input file name", "Input file's name", "Enter the input file's name : ");
        System.out.println(doesResourceExist(nameInput+".uberif.xml"));
        if (!nameInput.isEmpty() && doesResourceExist(nameInput+".uberif.xml")) {

            CityMap newMap = cityMapController.loadNewCityMap(nameInput+".uberif.xml", true);
            this.cityMap.merge(newMap);

            for (Courier courier : cityMap.getListCourier()) {

                if (!choiceCourier.getItems().contains(courier.getName()) && !courier.getName().isEmpty()) {
                    if (nbCouriers == 0) {
                        minusBtn.getStyleClass().remove("grey-state");
                        minusBtn.getStyleClass().add("blue-state");
                    }

                    nbCouriers++;
                    nbCourierLb.setText(String.valueOf(nbCouriers));
                    choiceCourier.getItems().add(courier.getName());

                    choiceCourier.getSelectionModel().select(choiceCourier.getItems().size() - 1);
                    selectedCourier = new Pair<>(courier, cityMap.getCourierDotMap().get(courier));
                }

            }

            update(this.cityMap, this.cityMap.getNodes());

        } else {
            showDialogWarningError("Error", "No output file found", "File : " + nameInput + ".uberif.xml");
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
        System.out.println("Plus click");
        // Create and execute the OptimizeRouteCommand
        PlusCommand plusCommand;
        plusCommand = new PlusCommand(this, context);
        invoker.setCommand(plusCommand);
        invoker.executeCommand();
        //optimizeCommand.execute();
        //this.cityMap = optimizeCommand.getCityMap();
        update(this.cityMap, this.cityMap.getNodes());

    }

    public void addCourier(String nameCourier) {
        if (!choiceCourier.getItems().contains(nameCourier)) {
            Random randomInt = new Random();
            Courier courier = new Courier(nameCourier,
                    Color.rgb(randomInt.nextInt(255), randomInt.nextInt(255), randomInt.nextInt(255)));
            cityMap.addCourier(courier);

            if (nbCouriers == 0) {
                minusBtn.getStyleClass().remove("grey-state");
                minusBtn.getStyleClass().add("blue-state");
            }

            nbCouriers++;
            nbCourierLb.setText(String.valueOf(nbCouriers));
            choiceCourier.getItems().add(courier.getName());
            choiceCourier.getSelectionModel().select(courier.getName());
            selectedCourier = new Pair<>(courier, cityMap.getCourierDotMap().get(courier));
            choiceCourier.getItems().sort(Comparator.comparing(Object::toString));

        } else {
            showDialogWarningError("Error", "This courier already exists", "Courier : " + nameCourier);
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


    public void showDialogWarningError(String title, String header, String content) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait();
    }


    @FXML
    protected void onMinusBtnClick() {
        if (nbCouriers > 0) {
            nbCouriers--;
            nbCourierLb.setText(String.valueOf(nbCouriers));

            Courier lastCourier = selectedCourier.getKey();
            if (!lastCourier.getName().isEmpty()) {
                // clear the dots of the courier
                for (Pair<Intersection, TimeWindow> intersectionPair : cityMap.getSelectedPairList(lastCourier)) {
                    intersectionPair.getKey().setFill(Color.RED);
                    intersectionPair.getKey().setRadius((height / 220) * coef);
                    intersectionPair.getKey().setIsOwned(false);

                    // cityMap.getCourierDotMap().get(noOne).add(intersectionPair);
                }
                cityMap.getTravelList().remove(lastCourier);
                cityMap.removeCourier(lastCourier);

                // If the last is selected
                if (choiceCourier.getItems().size() == 1) {
                    selectedCourier = new Pair<>(noOne, cityMap.getCourierDotMap().get(noOne));
                }
                choiceCourier.getItems().remove(lastCourier.getName());
                choiceCourier.getSelectionModel().selectFirst();
            }
            if (nbCouriers == 0) {
                minusBtn.getStyleClass().remove("blue-state");
                minusBtn.getStyleClass().add("grey-state");
            }
        }
        update(this.cityMap, this.cityMap.getNodes());
    }

    public Label getLbInfos() {
        return lbInfos;
    }

    private String formatMapName(String mapName) {
        switch (mapName.toLowerCase()) {
            case "small":
                return "Small Map";
            case "medium":
                return "Medium Map";
            case "large":
                return "Large Map";
            default:
                return "";
        }
    }

    public void updateGraphicalView(GraphicalView newView) {
        // Check if the new view is null
        if (newView == null) {
            throw new IllegalArgumentException("GraphicalView cannot be null.");
        }

        // Update controllers
        this.cityMapController = newView.cityMapController;
        this.paneController = newView.paneController;

        // Update models and services
        this.cityMap = newView.cityMap;
        this.xmlSerializer = newView.xmlSerializer;

        // Update UI components if necessary
        // (assuming these components are part of the GraphicalView)
        //this.mapPane = newView.mapPane;
        //this.choiceMap = newView.choiceMap;
        //this.choiceCourier = newView.choiceCourier;
        this.searchBox = newView.searchBox;
        //this.nbCourierLb = newView.nbCourierLb;
        this.lbInfos = newView.lbInfos;
        this.minusBtn = newView.minusBtn;



        // Update additional fields
        this.width = newView.width;
        this.height = newView.height;
        this.coef = newView.coef;
        this.selectedCourier = newView.selectedCourier;
        //this.context = newView.context;
        //this.invoker = newView.invoker;
        this.nbCouriers = newView.nbCouriers;
        nbCourierLb.setText(String.valueOf(nbCouriers));
        this.noOne = newView.noOne;

        this.update(this.cityMap, this.cityMap.getNodes());
    }


    protected void undo() {
        this.context.undo();
        this.updateGraphicalView(this.context.getState().getGraphicalView());
        //this.cityMap.addObserver(this);

        // Clear the items in the choice box
        choiceCourier.getItems().clear();

        update(this.cityMap, this.cityMap.getNodes());

        // Repopulate the choice box
        if (cityMap.getCourierDotMap() != null) {
            for (Courier courier : cityMap.getCourierDotMap().keySet()) {
                if (!courier.getName().isEmpty())
                choiceCourier.getItems().add(courier.getName());
            }
        }
        this.lbInfos = context.getState().getlbInfos();
        String selectedCourierName = context.getState().getSelectedCourier().getName();
        // Reselect the previously selected courier, if it exists
        if (selectedCourierName != null) {
            choiceCourier.getSelectionModel().select(selectedCourierName);
            for (Courier courier : cityMap.getCourierDotMap().keySet()) {
                if (courier.getName().equals(selectedCourierName)) {
                    selectedCourier = new Pair<>(courier, cityMap.getCourierDotMap().get(courier));
                    break;
                }
            }
        }

        choiceMap.getSelectionModel().select(formatMapName(cityMap.getMapName()));
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

        // Load initial map
        this.cityMap = cityMapController.loadNewCityMap((String) choiceMap.getSelectionModel().getSelectedItem(), false);
        this.cityMap.addObserver(this);

        addCourier("John");

        choiceCourier.getSelectionModel().selectedItemProperty().addListener(new ChoiceMenuCourierListener(this));

        // Add a listener to the width property of mapPane
        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            update(this.cityMap, this.cityMap.getNodes());
        });

        // Add a listener to the height property of mapPane
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            update(this.cityMap, this.cityMap.getNodes());
        });

        cityMap.addCourier(noOne);
        //selectedCourier = new Pair<>(noOne, cityMap.getCourierDotMap().get(noOne));

        mapPane.addEventHandler(MouseEvent.MOUSE_MOVED, new IntersectionHoverHandler(this, lbInfos));

        //KeyboardHandler keyboardListener = new KeyboardHandler(this);
        //mapPane.addEventHandler(KeyEvent.KEY_PRESSED, keyboardListener);

        this.context = new Context(this); // Assuming you have a default state
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
        Rectangle clip = new Rectangle(
                width, height
        );
        clip.setLayoutX(-(width - mapPane.getWidth()) / 2);
        clip.setLayoutY(-(height - mapPane.getHeight()) / 2);
        mapPane.setClip(clip);
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

            for(Courier courier : this.cityMap.getTravelList().keySet()) {
                List<Pair<Intersection, Date>> computedTravel = this.cityMap.getTravelList().get(courier);
                if (computedTravel == null) {
                    showDialogWarningError("Error", "There's no suitable tour for this Courier", "Courier : " + courier.getName());
                } else {
                    int j = 0;
                    List<Pair<RoadSegment, Date>> travel = new ArrayList<>();
                    for (Pair<Intersection, Date> p : computedTravel) {
                        if (!(p.getKey() == cityMap.getWarehouse().getIntersection()) || j == 0) {
                            List<RoadSegment> roadSegments = cityMap.getNodes().get(p.getKey());
                            for (RoadSegment r : roadSegments) {
                                if (r.getDestination() == computedTravel.get(j + 1).getKey()) {
                                    drawLine(r, courier.getColor());
                                    travel.add(new Pair<RoadSegment, Date>(r, computedTravel.get(j + 1).getValue()));
                                    j++;
                                    break;
                                }
                            }
                        }
                    }
                    courierTourDatas.add(new Pair<Courier, List<Pair<RoadSegment, Date>>>(courier, travel));
                    PDFRoadMap.generatePDF(computedTravel, courierTourDatas);
                }
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
            
            
            if (cityMap.getTravelList() != null) {
                for (Courier courier : cityMap.getTravelList().keySet()) {
                    List<Pair<Intersection, Date>> computedTravel = cityMap.getTravelPlan(courier);
                    if(computedTravel == null)
                    {
                        showDialogWarningError("Error", "There's no suitable tour for this Courier", "Courier : " + courier.getName());
                    }
                    else
                    {
                        int j = 0;
                        List<Pair<RoadSegment, Date>> travel = new ArrayList<>();
                        for(Pair<Intersection, Date> p : computedTravel) {
                            if(!(p.getKey() == cityMap.getWarehouse().getIntersection()) || j == 0) {
                                List<RoadSegment> roadSegments = cityMap.getNodes().get(p.getKey());
                                for(RoadSegment r : roadSegments) {
                                    if(r.getDestination() == computedTravel.get(j + 1).getKey())
                                    {
                                        drawLine(r, courier.getColor());
                                        travel.add(new Pair<RoadSegment, Date>(r, computedTravel.get(j + 1).getValue()));
                                        j++;
                                        break;
                                    }
                                }
                            }
                        }
                        courierTourDatas.add(new Pair<Courier, List<Pair<RoadSegment, Date>>>(courier, travel));
                        PDFRoadMap.generatePDF(computedTravel, courierTourDatas);
                    }
                    // for (Pair<Intersection, Date> p : cityMap.getTravelPlan(courier)) {
                    //     if (!(p.getKey() == cityMap.getWarehouse().getIntersection()) || cityMap.getTravelPlan(courier).indexOf(p) == 0) {
                    //         List<RoadSegment> roadSegments = cityMap.getNodes().get(p.getKey());
                    //         for (RoadSegment r : roadSegments) {
                    //             if (r.getDestination() == cityMap.getTravelPlan(courier).get(cityMap.getTravelPlan(courier).indexOf(p) + 1).getKey()) {
                    //                 drawLine(r, courier.getColor());
                    //                 break;
                    //             }
                    //         }
                    //     }
                    // }
                }
            }
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

    public Pane getMapPane() {
        return mapPane;
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

            //cityMap.getSelectedPairList(noOne).add(new Pair<>(intersection, new TimeWindow(null, null)));
            // if (cityMap.getSelectedPairList(noOne) != null) {
            //     cityMap.getSelectedPairList(noOne).add(new Pair<>(intersection, new TimeWindow(null, null)));
            // }
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
        double startX = paneController.getIntersectionX(segment.getOrigin(), width) - (width - mapPane.getWidth()) / 2;
        double startY = paneController.getIntersectionY(segment.getOrigin(), height) - (height - mapPane.getHeight()) / 2;
        double endX = paneController.getIntersectionX(segment.getDestination(), width) - (width - mapPane.getWidth()) / 2;
        double endY = paneController.getIntersectionY(segment.getDestination(), height) - (height - mapPane.getHeight()) / 2;

        Line road = new Line(startX, startY, endX, endY);
        road.setStroke(color);
        road.setStrokeWidth(3.0);

        // Create the arrowhead
        Polygon arrowHead = new Polygon();
        double arrowLength = 10; // The length of the arrowhead
        double arrowWidth = 5; // The width of the arrowhead at its base

        // Points for the arrowhead polygon
        arrowHead.getPoints().addAll(new Double[]{
                -arrowLength, -arrowWidth,
                -arrowLength, arrowWidth,
                0.0, 0.0
        });

        // Translate the arrowhead to the end of the line
        arrowHead.setTranslateX(endX);
        arrowHead.setTranslateY(endY);

        // Rotate the arrowhead to align with the line direction
        double angle = Math.atan2(endY - startY, endX - startX) * 180 / Math.PI;
        arrowHead.setRotate(angle);

        // Set the arrowhead color
        arrowHead.setFill(Color.BLACK);

        // Add the line and arrowhead to the map pane
        mapPane.getChildren().addAll(road, arrowHead);
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

    public void onKeyPressedHandler(KeyEvent event) {
        KeyboardHandler keyboardHandler = new KeyboardHandler(this);
        keyboardHandler.handle(event); // Assuming handleKeyEvent is a method in KeyboardHandler
    }

    public Context getContext() {
        return this.context;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public GraphicalView deepCopy() {
        GraphicalView copy = new GraphicalView();

        // Deep copy of cityMap
        copy.cityMap = this.cityMap.deepCopy();

        // Copying primitive and immutable fields
        copy.nbCouriers = this.nbCouriers;

        // Assuming Courier has a proper copy constructor or clone method
        copy.noOne = new Courier(this.noOne);
        copy.selectedCourier = this.selectedCourier;
        copy.lbInfos = this.lbInfos;

        // Copying context and invoker if necessary
        // This depends on how these should be treated in a deep copy (shared, independent, etc.)
        // copy.context = new Context(copy.cityMap); // Example, if a new context is needed
        // copy.invoker = new Invoker(); // Example, if a new invoker is needed

        // XMLSerializer, controllers, and other services might be shared or reinitialized
        copy.xmlSerializer = this.xmlSerializer; // Shared instance, or create a new one if independent
        copy.cityMapController = this.cityMapController; // Shared instance, or create new
        copy.paneController = this.paneController; // Shared instance, or create new

        // UI elements (Buttons, Labels, etc.) are typically not deep-copied
        // Instead, set up the UI state based on the copied data model

        return copy;
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