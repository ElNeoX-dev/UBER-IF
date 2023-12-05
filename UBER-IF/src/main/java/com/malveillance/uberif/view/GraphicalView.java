package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.CityMapController;
import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.CityMapService;
import com.malveillance.uberif.model.service.PaneService;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;


public class GraphicalView extends ShapeVisitor implements Observer {
    private CityMapController cityMapController;
    private PaneController paneController;

    //private Map<Courier, List<Intersection>> courierIntersectionMap = new HashMap<>();

    //private List<Dot> dotList = new ArrayList<>();
    private Map<Courier, List<Dot>> courierDotMap = new HashMap<>() ;

    private int nbCouriers = 0;

    private IntersectionClickHandler interClickHandle ;

    /*
    @FXML

    protected void onImportDataBtnClick() {
        System.out.println("Import data click");
    }
    */

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
        String nameCourier = showDialogBoxInput() ;
        if (nameCourier != null && !nameCourier.isEmpty()) {
            Random randomInt = new Random() ;
            Courier courier = new Courier(nameCourier, Color.rgb(randomInt.nextInt(255),randomInt.nextInt(255),randomInt.nextInt(255)));
            List<Dot> listDot = new ArrayList<Dot>();
            courierDotMap.put(courier, listDot) ;

            if (nbCouriers == 0) {
                minusBtn.getStyleClass().remove("grey-state");
                minusBtn.getStyleClass().add("blue-state");
            }

            nbCouriers++;
            nbCourierLb.setText(String.valueOf(nbCouriers));
            choiceCourier.getItems().add(courier.getName());



        }
    }

    public String showDialogBoxInput() {
        final String[] res = {null};
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter the courier's name");
        dialog.setHeaderText("Courier's name");
        dialog.setContentText("Enter the courier's name : ");

        dialog.showAndWait().ifPresent(result -> {
            res[0] = result ;
        });
        return res[0];
    }


    @FXML
    protected void onMinusBtnClick() {
        if (nbCouriers > 0) {
            nbCouriers--;
            nbCourierLb.setText(String.valueOf(nbCouriers));

            Courier courierRemoved = listI

            // clear the dots of the courier
            List<Circle> listCircle = courierDotMap.get(pairCourierRemoved.getKey()) ;
            for (Circle intersectionDot: listCircle) {
                intersectionDot.setFill(Color.RED);
                intersectionDot.setRadius(height/250);
            }
            listCircle.clear() ;

            listCourierIntersec.remove(listCourierIntersec.size()-1);

            //If the last is selected
            if (choiceCourier.getSelectionModel().isSelected(choiceCourier.getItems().size()-1)) {
                choiceCourier.getSelectionModel().selectFirst();
            }
            choiceCourier.getItems().remove(choiceCourier.getItems().size()-1);
        }
        if (nbCouriers == 0) {
            minusBtn.getStyleClass().remove("blue-state");
            minusBtn.getStyleClass().add("grey-state");
        }
    }

    @FXML
    private TextField searchBox ;

    @FXML
    protected void onSearchBoxTyped() {
        for (Dot dot: dotList) {
            if (!searchBox.getText().isEmpty() && dot.getIntersection().getId().contains(searchBox.getText())) {
                dot.setRadius(height/100);
                if (dot.getOwner() == null) {
                    dot.setFill(Color.BLACK);
                }
            } else {
                if (dot.getOwner() != null) {
                    dot.setFill(dot.getOwner().getColor());
                    dot.setRadius(height/220);
                } else {
                    dot.setFill(Color.RED);
                    dot.setRadius(height/250);
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

    double width ;
    double height ;

    private CityMap cityMap;

    private Pair<Courier, List<Dot>> selectedCourier ;


    @FXML
    public void initialize() {
        // Initialize UI
        minusBtn.getStyleClass().add("grey-state");

        // Create a list of choices
        ObservableList<String> mapChoices = FXCollections.observableArrayList("Small Map", "Medium Map", "Large Map");

        // Set the choices in the ChoiceBox
        choiceMap.setItems(mapChoices);

        // Optionally, set a default selected item
        choiceMap.getSelectionModel().selectFirst();

        // Add a listener
        choiceMap.getSelectionModel().selectedItemProperty().addListener(new ChoiceMenuMapListener(cityMapController, this, paneController));
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

        Courier courier = new Courier("", Color.RED);
        selectedCourier = new Pair<>(courier, new ArrayList<>()) ;
        listCourierIntersec.add(selectedCourier);

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
        if(mapPane.getWidth() > mapPane.getHeight())
        {
            width = mapPane.getHeight();
            height = width;
        }
        else
        {
            width = mapPane.getWidth();
            height = width;
        }


        dotList.removeIf(dot -> dot.getOwner()==null);

        if (o instanceof CityMap newmap) {
            mapPane.getChildren().clear();
            // Draw lines first
            newmap.getWarehouse().accept(this);
            for (Shape elem : newmap.getNodes().keySet()) {
                elem.accept(this);
            }
            /*
            for (List<RoadSegment> list : newmap.getNodes().values()) {
                for (Shape elem : list) {
                    elem.accept(this);
                }
            }
             */

            this.cityMap = newmap;

            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image("file:src/main/resources/com/malveillance/uberif/"+cityMap.getMapName()+"Map.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(width, height, false, false, false, false)
            );

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
        Circle intersectionDot = new Circle(intersectionX, intersectionY, height/200, Color.GREEN);
        mapPane.getChildren().add(intersectionDot);
    }

    public void visit(Intersection intersection) {
        // Implementation to draw intersection
        double intersectionX = paneController.getIntersectionX(intersection, width);
        double intersectionY = paneController.getIntersectionY(intersection, height);
        intersectionX = intersectionX + (width - mapPane.getWidth()) / 2;
        intersectionY = intersectionY + (height - mapPane.getHeight()) / 2;
        Dot intersectionDot = new Dot(null, intersection, intersectionX, intersectionY, height/250, Color.RED);
        if ( (intersectionX != paneController.getIntersectionX(cityMap.getWarehouse().intersection, width)) && (intersectionY != paneController.getIntersectionY(cityMap.getWarehouse().intersection, height)))
            {
                if (!dotList.contains(intersectionDot)) {
                    dotList.add(intersectionDot);
                    mapPane.getChildren().add(intersectionDot);
                    intersectionDot.addEventHandler(MouseEvent.MOUSE_ENTERED, new IntersectionHoverHandler(intersection, lbInfos));

                    interClickHandle = new IntersectionClickHandler(intersectionDot, intersection, this);
                    intersectionDot.addEventHandler(MouseEvent.MOUSE_CLICKED, interClickHandle);
                }
            }
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

    public void setSelectedCourier(Pair<Courier, List<Dot>> selectedCourier) {
        this.selectedCourier = selectedCourier;
    }


    public Map<Courier, List<Dot>> getCourierDotMap() {
        return courierDotMap;
    }

    /*
    public void printMinMaxLatLong() {
        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLong = Double.MAX_VALUE;
        double maxLong = Double.MIN_VALUE;


        for (Intersection inter : this.cityMap.getNodes().keySet()) {
            if (inter.getLatitude() < minLat) {
                minLat = inter.getLatitude();
            } else if (inter.getLatitude() > maxLat) {
                maxLat = inter.getLatitude();
            }

            if (inter.getLongitude() < minLong) {
                minLong = inter.getLongitude();
            } else if (inter.getLongitude() > maxLong) {
                maxLong = inter.getLongitude();
            }
        }

        System.out.println("minLat : " + minLat + "\nmaxLat : " + maxLat + "\nminLong : " + minLong + "\nmaxLong : " + maxLong);
    }*/

}