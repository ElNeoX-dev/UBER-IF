package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.TimeWindow;
import com.malveillance.uberif.view.GraphicalView;
import javafx.util.Pair;

/**
 * The class represents a command for selecting a delivery address to be added to a courier's route.
 * It is associated with the "Select" button in the graphical user interface when in the selection state.
 */
public class SelectCommand implements Command {
    /**
     * The graphical view associated with the command
     */
    private GraphicalView graphicalView;
    /**
     * The courier for which the address is being selected
     */
    private Courier courier;
    /**
     * The selected pair of intersection and time window
     */
    private Pair<Intersection, TimeWindow> pair;

    /**
     * Constructs a new SelectCommand
     * @param graphicalView the graphical view associated with the command
     * @param courier       the courier for which the address is being selected
     * @param pair          the selected pair of intersection and time window
     */
    public SelectCommand(GraphicalView graphicalView, Courier courier, Pair<Intersection, TimeWindow> pair) {
        this.graphicalView = graphicalView;
        this.courier = courier;
        this.pair = pair;
    }

    /**
     * Executes the command by adding the selected delivery address to the courier's route.
     * It calls the necessary logic in the graphical view and transitions to the new state.
     */
    @Override
    public void execute() {
        graphicalView.getCityMap().getSelectedPairList(courier).add(pair);
        SelectState newState = new SelectState(graphicalView);
        graphicalView.getContext().setState(newState);
    }
}
