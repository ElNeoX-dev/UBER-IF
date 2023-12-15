package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.TimeWindow;
import com.malveillance.uberif.view.GraphicalView;
import javafx.util.Pair;

public class SelectCommand implements Command {
    // Variables to store command state
    private GraphicalView graphicalView;
    private Courier courier;
    private Pair<Intersection, TimeWindow> pair;

    public SelectCommand(GraphicalView graphicalView, Courier courier, Pair<Intersection, TimeWindow> pair) {
        this.graphicalView = graphicalView;
        this.courier = courier;
        this.pair = pair;
    }
    @Override
    public void execute() {
        // Logic to add an address
        graphicalView.getCityMap().getSelectedPairList(courier).add(pair);
        SelectState newState = new SelectState(graphicalView);
        graphicalView.getContext().setState(newState);
    }

//    @Override
//    public void undo() {
//        // Logic to undo adding an address
//    }
}