package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.TimeWindow;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * The class represents a command for removing a courier from the application.
 * It is associated with the "Minus" button in the graphical user interface.
 */
public class MinusCommand implements Command {
    /**
     * The graphical view associated with the command
     */
    private GraphicalView graphicalView;
    /**
     * The context managing the state transitions
     */
    private Context context;

    /**
     * Constructs a new MinusCommand
     * @param graphicalView the graphical view associated with the command
     * @param context       the context managing the state transitions
     */
    public MinusCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    /**
     * Executes the removal of a courier from the application.
     * It clears the dots associated with the courier, updates the graphical view, and notifies the context.
     */
    @Override
    public void execute() {
        // CityMap previousCityMap = cityMap.deepCopy();

        if (graphicalView.getNbCouriers() > 0) {
            graphicalView.getNbCouriers();
            graphicalView.getNbCourierLb().setText(String.valueOf(graphicalView.getNbCouriers()));

            Courier lastCourier = graphicalView.getSelectedCourier().getKey();
            if (!lastCourier.getName().isEmpty()) {
                // clear the dots of the courier
                for (Pair<Intersection, TimeWindow> intersectionPair : graphicalView.getCityMap().getSelectedPairList(lastCourier)) {
                    intersectionPair.getKey().setFill(Color.RED);
                    intersectionPair.getKey().setRadius((graphicalView.getHeight() / 220) * graphicalView.getCoef());
                    intersectionPair.getKey().setIsOwned(false);

                    // cityMap.getCourierDotMap().get(noOne).add(intersectionPair);
                }
                graphicalView.getCityMap().getTravelList().remove(lastCourier);
                graphicalView.getCityMap().removeCourier(lastCourier);

                // If the last is selected
                if (graphicalView.getChoiceCourier().getItems().size() == 1) {
                    graphicalView.setSelectedCourier(new Pair<>(graphicalView.getNoOne(), graphicalView.getCityMap().getCourierDotMap().get(graphicalView.getNoOne())));
                }
                graphicalView.getChoiceCourier().getItems().remove(lastCourier.getName());
                graphicalView.getChoiceCourier().getSelectionModel().selectFirst();
            }
            if (graphicalView.getNbCouriers() == 0) {
                graphicalView.getMinusBtn().getStyleClass().remove("blue-state");
                graphicalView.getMinusBtn().getStyleClass().add("grey-state");
            }
        }
        context.handleInput("minus", graphicalView);
    }

//    @Override
//    public void undo() {
//        if (previousState != null) {
//            // Restore the previous state
//
//        }
//    }
}
