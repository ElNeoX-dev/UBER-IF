package com.malveillance.uberif.view;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

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

        Courier courier = graphicalView.getSelectedCourier().getKey();
        List<Intersection> selectedListIntersections = courier.getSelectedIntersectionList();




        if (!courier.getName().isEmpty()) {
            if (selectedListIntersections.contains(intersection)) {
                selectedListIntersections.remove(intersection);
                //intersectionDot.setOwner(null);
                intersection.setFill(Color.RED);
            } else {
                selectedListIntersections.add(intersection);
                //intersectionDot.setOwner(courier);
                intersection.setFill(courier.getColor());
                intersection.setRadius(graphicalView.height/220);
            }
        }
    }
}
