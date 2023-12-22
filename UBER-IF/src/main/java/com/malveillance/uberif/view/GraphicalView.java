package com.malveillance.uberif.view;

import com.malveillance.uberif.formatters.PDFRoadMap;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.net.URL;
import java.util.*;

/**
 * The class represents the graphical view, used to display the city map and handles the user interactions.
 */
public class GraphicalView extends ShapeVisitor implements Observer {

    /**
     * The city map controller
     */
    private CityMapController cityMapController;

    /**
     * The pane controller
     */
    private PaneController paneController;

    /**
     * The context
     */
    private Context context;

    /**
     * The invoker
     */
    private Invoker invoker;

    /**
     * The XML serializer
     */
    private XMLserializer xmlSerializer;

    /**
     * The list of couriers and their tours
     */
    private List<Pair<Courier, List<Pair<RoadSegment, Date>>>> courierTourDatas;

    /**
     * The number of couriers
     */
    private int nbCouriers = 0;

    /**
     * The no one courier used when no courier is selected
     */
    private Courier noOne = new Courier("", Color.RED);


    /**
     * Handles the optimize button click event and optimizes the routes using the TSP algorithm
     */
    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");

        courierTourDatas = new ArrayList<>();
        mapPane.getChildren().clear();
        update(this.cityMap, this.cityMap.getNodes());
        for(Courier courier : cityMap.getCourierDotMap().keySet()) {
            if(!courier.getName().isEmpty()) {
                List<Pair<Intersection, TimeWindow>> deliveryPoints = cityMap.getSelectedPairList(courier);
                Tour tour = new Tour(new Delivery(cityMap.getWarehouse().getIntersection(), new TimeWindow(0)));
                for (Pair<Intersection, TimeWindow> d : deliveryPoints) {
                    tour.addDelivery(new Delivery(d.getKey(), d.getValue()));
                }
                courier.setCurrentTour(tour);
                List<Pair<Intersection, Date>> computedTravel = AlgoService.calculateOptimalRoute(cityMap, tour);
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
            }
        }
    }

    /**
     * Handles the save button click event and saves the city map
     */
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

    /**
     * Handles the restore button click event and restores the city map
     */
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

    /**
     * Handles the mouse entered event on an intersection
     */
    @FXML
    protected void onMouseEntered(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setCursor(javafx.scene.Cursor.HAND);
        }
    }

    /**
     * Handles the exit of the mouse on an intersection
     */
    @FXML
    protected void onMouseExited(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Handles the plus button click event
     */
    @FXML
    protected void onPlusBtnClick() {
        String nameCourier = showDialogBoxInput("Enter the courier's name", "Courier's name", "Enter the courier's name : ");

        if (nameCourier != null && !nameCourier.isEmpty()) {

            addCourier(nameCourier);

        }


    }

    /**
     * Adds a courier
     * @param nameCourier the name of the courier
     */
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

    /**
     * Shows a dialog box with an input
     * @param title the title of the dialog box
     * @param header the header of the dialog box
     * @param content the content of the dialog box
     * @return the input
     */
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


    /**
     * Shows a dialog box with a warning message
     * @param title the title of the dialog box
     * @param header the header of the dialog box
     * @param content the content of the dialog box
     */
    public void showDialogWarningError(String title, String header, String content) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait();
    }


    /**
     * Handles the minus button click event
     */
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
    }

    @FXML
    private TextField searchBox;

    /**
     * Handles the search box typed event (when the user types in the search box)
     */
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

    /**
     * Initializes the UI
     */
    @FXML
    public void initialize() {

        this.context = new Context(); // Assuming you have a default state
        this.invoker = new Invoker();

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

    }

    /**
     * Sets the city map controller
     * @param cityMapController the city map controller
     */
    public void setCityMapController(CityMapController cityMapController) {
        this.cityMapController = cityMapController;
    }

    /**
     * Sets the pane controller
     * @param paneController the pane controller
     */
    public void setPaneController(PaneController paneController) {
        this.paneController = paneController;
    }

    /**
     * Constructs a new GraphicalView.
     */
    public GraphicalView() {
        CityMapService cityMapService = new CityMapService();
        PaneService paneService = new PaneService();
        cityMapController = new CityMapController(cityMapService);
        paneController = new PaneController(paneService);
    }

    /**
     * Updates the UI
     * @param o the observable
     * @param arg the argument (in general teh MapPane)
     */

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

            if(courierTourDatas != null)
            {
                for(Pair<Courier, List<Pair<RoadSegment, Date>>> c : courierTourDatas) {
                    for(Pair<RoadSegment, Date> p : c.getValue()) {
                        drawLine(p.getKey(), c.getKey().getColor());
                    }
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
        }

    }

    /**
     * Draws a circle on the map pane
     * @param warehouse the warehouse
     */
    public void visit(Warehouse warehouse) {
        // Implementation to draw warehouse
        double intersectionX = paneController.getIntersectionX(warehouse.intersection, width);
        double intersectionY = paneController.getIntersectionY(warehouse.intersection, height);
        intersectionX = intersectionX - (width - mapPane.getWidth()) / 2;
        intersectionY = intersectionY - (height - mapPane.getHeight()) / 2;
        warehouse.getIntersection().setCircle(intersectionX, intersectionY, (height / 150) * coef, Color.GREEN);
        mapPane.getChildren().add(warehouse.getIntersection().getCircle());
    }

    /**
     * Draws a circle on the map pane
     * @param intersection the intersection
     */
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
            mapPane.getChildren().add(intersection.getCircle());
            intersection.getCircle().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new IntersectionClickHandler(intersection, this));

        }
    }

    /**
     * Draws a line on the map pane
     * @param segment the road segment
     */
    public void visit(RoadSegment segment) {
        // Implementation to draw segment
        drawLine(segment, Color.GREY);
    }


    /**
     * Draws a line on the map pane
     * @param segment the road segment
     * @param color the color of the line
     */
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

    /**
     * Returns the city map
     * @return the city map
     */
    public CityMap getCityMap() {
        return this.cityMap;
    }

    /**
     * Sets the selected courier
     * @param selectedCourier the selected courier
     */
    public void setSelectedCourier(Pair<Courier, List<Pair<Intersection, TimeWindow>>> selectedCourier) {
        this.selectedCourier = selectedCourier;
    }

    /**
     * Returns the selected courier
     * @return the selected courier
     */
    public Pair<Courier, List<Pair<Intersection, TimeWindow>>> getSelectedCourier() {
        return selectedCourier;
    }

    /**
     * Checks if the resource exists
     * @param resourceName the resource name
     * @return true if the resource exists, false otherwise
     */
    public boolean doesResourceExist(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("output/"+resourceName);
        return resource != null;
    }

}