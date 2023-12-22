package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

/**
 * The class represents the InitialState.
 */
class InitialState implements State {

    private GraphicalView graphicalView = null;

        /**
     * Constructs a new InitialState.
     */
    public InitialState(GraphicalView graphicalView){
        this.graphicalView = graphicalView.deepCopy();
    }
    @Override
    public void handleInput(Context context, String input, GraphicalView graphicalView) {
        if (input.equals("selectCourier")) {
            context.setState(new SelectState(graphicalView));
        }
        if (input.equals("optimize")) {
            context.setState(new SelectState(graphicalView));
        }
        // Other input handling for InitialState
    }

    @Override
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    @Override
    public Courier getSelectedCourier() {return graphicalView.getSelectedCourier().getKey();};

    @Override
    public Label getlbInfos() {return graphicalView.getLbInfos();}
}