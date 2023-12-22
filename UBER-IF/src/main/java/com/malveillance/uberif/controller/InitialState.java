package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

/**
 * The class represents the initial state of the application.
 * It defines the behavior of the application when it is in the initial state.
 */
class InitialState implements State {

    /**
     * The graphical view associated with the state.
     */
    private GraphicalView graphicalView = null;

    /**
     * Constructs a new InitialState
     * @param graphicalView the graphical view associated with the state
     */
    public InitialState(GraphicalView graphicalView) {
        this.graphicalView = graphicalView.deepCopy();
    }

    /**
     * Handles user input based on the current initial state
     * @param context       the context managing the state transitions
     * @param input         the user input
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
    }

    /**
     * Gets the graphical view associated with the state
     * @return the graphical view
     */
    @Override
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    /**
     * Gets the currently selected courier in the state
     * @return the selected courier
     */
    @Override
    public Courier getSelectedCourier() {
        return graphicalView.getSelectedCourier().getKey();
    }

    /**
     * Gets the label providing information in the graphical view
     * @return the information label
     */
    @Override
    public Label getlbInfos() {
        return graphicalView.getLbInfos();
    }
}
