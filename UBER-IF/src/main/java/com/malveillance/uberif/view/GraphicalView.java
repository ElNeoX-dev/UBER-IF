package com.malveillance.uberif.view;

import com.malveillance.uberif.formatters.PDFRoadMap;
import com.malveillance.uberif.controller.*;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.Shape;
import com.malveillance.uberif.model.service.AlgoService;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import com.malveillance.uberif.util.ResourceReader;
import com.malveillance.uberif.xml.XMLserializer;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * The class represents the graphical view, used to display the city map and handles the user interactions.
 */
public class GraphicalView extends ShapeVisitor implements Observer {
    double width;
    double height;
    double coef = 1.0;

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
    @FXML
    private TextField searchBox;
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
    private CityMap cityMap;
    private Pair<Courier, List<Pair<Intersection, TimeWindow>>> selectedCourier;

    public GraphicalView() {
        CityMapService cityMapService = new CityMapService();
        PaneService paneService = new PaneService();
        cityMapController = new CityMapController(cityMapService);
        paneController = new PaneController(paneService);
    }

    /**
     * Handles the optimize button click event and optimizes the routes using the TSP algorithm
     */
    @FXML
    protected void onOptimizeBtnClick() {
        System.out.println("Optimize click");
        OptimizeRouteCommand optimizeCommand;
        optimizeCommand = new OptimizeRouteCommand(this, context);
        invoker.setCommand(optimizeCommand);
        invoker.executeCommand();
        update(this.cityMap, this.cityMap.getNodes());
    }

    JFileChooser chooser;

    /**
     * Handles the save button click event and saves the city map
     */

    @FXML
    protected void onSaveBtnClick() {
        System.out.println("Save click");
        SaveCommand saveCommand;
        saveCommand = new SaveCommand(this, context);
        invoker.setCommand(saveCommand);
        invoker.executeCommand();
        update(this.cityMap, this.cityMap.getNodes());

    }

    public void setNbCourierLb(Label nbCourierLb) {
        this.nbCourierLb = nbCourierLb;
    }

    public void setLbInfos(Label lbInfos) {
        this.lbInfos = lbInfos;
    }

    /**
     * Handles the restore button click event and restores the city map
     */
    @FXML
    protected void onRestoreBtnClick() {
        System.out.println("Restore click");
        RestoreCommand restoreCommand;
        restoreCommand = new RestoreCommand(this, context);
        invoker.setCommand(restoreCommand);
        invoker.executeCommand();
        update(this.cityMap, this.cityMap.getNodes());
    }

    public String showFileChooser() {
        String path = "";
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Please choose the .uberif file to load");
        File defaultDirectory = new File("./..");
        chooser.setInitialDirectory(defaultDirectory);
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Uber'IF Files", "*.uberif")
        );
        File selectedFile = chooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            path = selectedFile.getPath();
        }
        return path;
    }

    @FXML
    public void onSaveFdRBtnClick() {
        System.out.println("Save roadMap click");
        if (courierTourDatas != null && !courierTourDatas.isEmpty()) {
            String path = showDirectoryChooser() ;
            String nameOutput = showDialogBoxInput("Enter the output file name", "Output file's name", "Enter the output file's name : ");
            if (!path.isEmpty() && !nameOutput.isEmpty()) {
                PDFRoadMap.generatePDF(path, nameOutput, courierTourDatas);
            } else {
                showDialogWarningError("Error", "The file name is empty", "");
            }
        } else {
            showDialogWarningError("Error", "The route must be calculated before saving a roadmap", "Please click on 'Calculate routes' before saving the roadmap");
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

    public void setCourierTourDatas(List<Pair<Courier, List<Pair<RoadSegment, Date>>>> courierTourDatas) {
        this.courierTourDatas = courierTourDatas;
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
        System.out.println("Plus click");
        PlusCommand plusCommand;
        plusCommand = new PlusCommand(this, context);
        invoker.setCommand(plusCommand);
        invoker.executeCommand();
        update(this.cityMap, this.cityMap.getNodes());

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
        final String[] res = {null};
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait().ifPresent(result -> {
            res[0] = result;
        });
        return res[0];
    }

    public String showDirectoryChooser() {
        String path = "" ;
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Please choose the destination directory for the output");
        File defaultDirectory = new File("./..");
        chooser.setInitialDirectory(defaultDirectory);

        File selectedDirectory = chooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            path = selectedDirectory.getPath() + "/";
        }
        return path;
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

    public Courier getNoOne() {
        return noOne;
    }

    public Label getNbCourierLb() {
        return nbCourierLb;
    }

    public double getHeight() {
        return height;
    }

    public int getNbCouriers() {
        return nbCouriers;
    }

    public void setNbCouriers(int nbCouriers) {
        this.nbCouriers = nbCouriers;
    }

    public Button getMinusBtn() {
        return minusBtn;
    }

    public ChoiceBox getChoiceCourier() {
        return choiceCourier;
    }

    @FXML
    protected void onMinusBtnClick() {
        System.out.println("Minus click");
        MinusCommand minusCommand;
        minusCommand = new MinusCommand(this, context);
        invoker.setCommand(minusCommand);
        invoker.executeCommand();
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
        this.cityMap = newView.cityMap.deepCopy();
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

        this.courierTourDatas = newView.courierTourDatas;

        this.update(this.cityMap, this.cityMap.getNodes());
    }

    protected void undo() {
        this.context.undo();
        this.updateGraphicalView(this.context.getState().getGraphicalView());
        //this.cityMap.addObserver(this);

        // Clear the items in the choice box
        choiceCourier.getItems().clear();

        //update(this.cityMap, this.cityMap.getNodes());

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
        update(this.cityMap, this.cityMap.getNodes());
    }

    protected void redo() {
        this.context.redo();
        this.updateGraphicalView(this.context.getState().getGraphicalView());
        //this.cityMap.addObserver(this);

        // Clear the items in the choice box
        choiceCourier.getItems().clear();

        //update(this.cityMap, this.cityMap.getNodes());

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
        update(this.cityMap, this.cityMap.getNodes());
    }

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

            //newmap.setCourierDotMap(cityMap.getCourierDotMap());
            mapPane.getChildren().clear();
            // Draw lines first
            newmap.getWarehouse().accept(this);
            // newmap.addCourier(noOne);

            for (Shape elem : newmap.getNodes().keySet()) {
                elem.accept(this);
            }


            //this.cityMap = newmap;

            ResourceReader rsReader = new ResourceReader();
            InputStream is = rsReader.getFileAsIOStream(cityMap.getMapName() + "Map.png");

            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image(is),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(width, height, false, false, false, false));

            Background background = new Background(backgroundImage);
            mapPane.setBackground(background);

            // printMinMaxLatLong();

            paneController.updateScale(newmap.getNodes().keySet());


            setCourierTourDatas(new ArrayList<>());
            if (newmap.getTravelList() != null) {
                for (Courier courier : newmap.getTravelList().keySet()) {
                    List<Pair<Intersection, Date>> computedTravel = newmap.getTravelPlan(courier);
                    if (computedTravel == null) {
                        showDialogWarningError("Error", "There's no suitable tour for this Courier", "Courier : " + courier.getName());
                    } else {
                        int j = 0;
                        List<Pair<RoadSegment, Date>> travel = new ArrayList<>();
                        for (Pair<Intersection, Date> p : computedTravel) {
                            if (!(p.getKey() == newmap.getWarehouse().getIntersection()) || j == 0) {
                                List<RoadSegment> roadSegments = newmap.getNodes().get(p.getKey());
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
                    }
                }
            }
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

    public Pane getMapPane() {
        return mapPane;
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
            // if (cityMap.getSelectedPairList(noOne) != null) {
            //     cityMap.getSelectedPairList(noOne).add(new Pair<>(intersection, new TimeWindow(null, null)));
            // }
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

    public double getCoef() {
        return coef;
    }
        /**
         * Sets the selected courier
         * @param selectedCourier the selected courier
         */
        public void setSelectedCourier (Pair < Courier, List < Pair < Intersection, TimeWindow >>> selectedCourier){
            this.selectedCourier = selectedCourier;
        }

        /**
         * Returns the selected courier
         * @return the selected courier
         */
        public Pair<Courier, List<Pair<Intersection, TimeWindow>>> getSelectedCourier () {
            return selectedCourier;
        }


        public void onKeyPressedHandler (KeyEvent event){
            KeyboardHandler keyboardHandler = new KeyboardHandler(this);
            keyboardHandler.handle(event); // Assuming handleKeyEvent is a method in KeyboardHandler
        }

        public Context getContext () {
            return this.context;
        }

        public Invoker getInvoker () {
            return this.invoker;
        }

        public GraphicalView deepCopy () {
            GraphicalView copy = new GraphicalView();

            // Deep copy of cityMap
            copy.cityMap = this.cityMap.deepCopy();

            // Copying primitive and immutable fields
            copy.nbCouriers = this.nbCouriers;
            copy.courierTourDatas = this.courierTourDatas;

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

}