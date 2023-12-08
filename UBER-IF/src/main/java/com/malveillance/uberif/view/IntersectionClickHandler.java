package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
        //System.out.println("Mouse clicked over intersection (" + intersection.getId() + ") at (" + intersection.getLatitude() + ", " + intersection.getLongitude() + ")");

        Courier currentCourier = graphicalView.getSelectedCourier().getKey();

        if (!currentCourier.getName().isEmpty()) {
            if (graphicalView.getCityMap().getSelectedIntersectionList(currentCourier).contains(intersection)) {
                graphicalView.getCityMap().getSelectedIntersectionList(currentCourier).remove(intersection);
                intersection.setIsOwned(false);
                intersection.setFill(Color.RED);
            } else {
                graphicalView.getCityMap().getSelectedIntersectionList(currentCourier).add(intersection);
                intersection.setIsOwned(true);
                intersection.setFill(currentCourier.getColor());
                intersection.getCircle().setRadius(graphicalView.height/150);

            }
        }
    }

    public String showDialogBoxInput() {
        final String[] res = {null};
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter a time window");
        dialog.setHeaderText("Courier's name");
        dialog.setContentText("Enter the courier's name : ");

        dialog.showAndWait().ifPresent(result -> {
            res[0] = result ;
        });
        return res[0];
    }
}
