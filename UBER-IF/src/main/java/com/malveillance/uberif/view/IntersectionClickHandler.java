package com.malveillance.uberif.view;

import com.malveillance.uberif.controller.OptimizeRouteCommand;
import com.malveillance.uberif.controller.SelectCommand;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.TimeWindow;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IntersectionClickHandler implements EventHandler<MouseEvent> {
    private Intersection intersection;

    private GraphicalView graphicalView;

    public IntersectionClickHandler(Intersection intersection, GraphicalView graphicalView) {
        this.intersection = intersection;
        this.graphicalView = graphicalView;
    }

    @Override
    public void handle(MouseEvent event) {
        // System.out.println("Mouse clicked over intersection (" + intersection.getId()
        // + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude()
        // + ")");
        Courier currentCourier = graphicalView.getSelectedCourier().getKey();

        if (!currentCourier.getName().isEmpty()) {
            // To unselect a circle : press shift + click
            if (event.isShiftDown()) {
                intersection.setIsOwned(false);
                intersection.setFill(Color.RED);
                // Reinitialize the pair containing the intersection and the time window
                for (Pair<Intersection, TimeWindow> intersectionPair : graphicalView.getCityMap()
                        .getSelectedPairList(currentCourier)) {
                    if (intersectionPair.getKey().equals(intersection)) {
                        intersectionPair.getValue().setStartingTime(null);
                        intersectionPair.getValue().setEndingTime(null);
                        graphicalView.getCityMap().getSelectedPairList(currentCourier).remove(intersectionPair);
                        break;
                    }
                }

                graphicalView.getCityMap().getSelectedPairList(currentCourier)
                        .removeIf(pair -> pair.getKey().equals(intersection));

            } else {
                if (graphicalView.getCityMap().seeSelectedIntersectionList(currentCourier).contains(intersection)) {

                } else {
                    String result = showChoiceDialogTime();
                    int startH = -1;
                    try {
                        startH = Integer.parseInt(result.replace("h", ""));
                    } catch (NumberFormatException err) {
                        System.out.println("Erreur lors du parseInt : " + err);
                    }

                    if (!result.isEmpty() && startH >= 8 && startH <= 11) {
                        intersection.setIsOwned(true);
                        intersection.setFill(currentCourier.getColor());
                        intersection.getCircle().setRadius(graphicalView.height / 150);


                        TimeWindow timeWindow = new TimeWindow(startH, 60);

                        System.out.println(timeWindow.getStartingTime() + " " + timeWindow.getEndingTime());

                        Pair<Intersection,TimeWindow> selectedPair= new Pair<>(intersection, timeWindow);
                        SelectCommand selectCommand;
                        selectCommand = new SelectCommand(graphicalView, currentCourier, selectedPair);
                        graphicalView.getInvoker().setCommand(selectCommand);
                        graphicalView.getInvoker().executeCommand();
                        //graphicalView.getCityMap().getSelectedPairList(currentCourier)
                        //       .add(new Pair<>(intersection, timeWindow));
                    }

                }
            }
        }

    }

    public String showChoiceDialogTime() {
        final String[] res = { "" };
        ChoiceDialog dialog = new ChoiceDialog("8h");
        dialog.setTitle("Enter a time window");
        dialog.setHeaderText("Enter a time window");
        dialog.setContentText("Please select the wanted delivery time : ");

        ObservableList<String> list = dialog.getItems();
        list.add("8h");
        list.add("9h");
        list.add("10h");
        list.add("11h");

        dialog.showAndWait().ifPresent(result -> res[0] = dialog.getResult().toString());

        return res[0];
    }

    public String showDialogBoxInput() {
        final String[] res = { null };
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter a time window");
        dialog.setHeaderText("Courier's name");
        dialog.setContentText("Enter the courier's name : ");

        dialog.showAndWait().ifPresent(result -> {
            res[0] = result;
        });
        return res[0];
    }
}
