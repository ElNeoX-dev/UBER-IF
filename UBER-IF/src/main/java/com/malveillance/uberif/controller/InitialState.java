package com.malveillance.uberif.controller;

import com.malveillance.uberif.controller.PaneController;
import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

class InitialState implements State {

    private GraphicalView graphicalView = null;

    public InitialState(GraphicalView graphicalView){
        this.graphicalView = graphicalView.deepCopy();
    }
    @Override
    public void handleInput(Context context, String input) {
        if (input.equals("selectCourier")) {
            context.setState(new SelectState(this.graphicalView));
        }
        // Other input handling for InitialState
    }

    @Override
    public CityMap getCityMap() {
        return graphicalView.getCityMap();
    }

    @Override
    public Courier getSelectedCourier() {return graphicalView.getSelectedCourier().getKey();};

    @Override
    public Label getlbInfos() {return graphicalView.getLbInfos();}
}