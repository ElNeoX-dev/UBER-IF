package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

/**
 * The class represents the state where the user is in the process of selecting a courier's route.
 * It is associated with the "Select" button in the graphical user interface.
 */
class SelectState implements State {
    private GraphicalView graphicalView = null;

    /**
     * Constructs a new SelectState
     * @param graphicalView the graphical view associated with the state
     */
    public SelectState(GraphicalView graphicalView){
        this.graphicalView= graphicalView.deepCopy();
    }

    /**
     * Handles the input based on the user actions
     * @param context       the context
     * @param input         the input
     * @param graphicalView the graphical view associated with the state
     */
    @Override
    public void handleInput(Context context, String input, GraphicalView graphicalView) {
        if (input.equals("selectCourier")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("optimize")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("plus")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("minus")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("save")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("restore")) {
            context.setState(new SelectState(graphicalView));
        }
    }

    /**
     * Gets the associated graphical view
     * @return the graphical view
     */
    @Override
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    /**
     * Gets the currently selected courier
     * @return the selected courier
     */
    @Override
    public Courier getSelectedCourier() {
        return graphicalView.getSelectedCourier().getKey();
    }

    /**
     * Gets the label containing information
     * @return the information label
     */
    @Override
    public Label getlbInfos() {
        return graphicalView.getLbInfos();
    }
}