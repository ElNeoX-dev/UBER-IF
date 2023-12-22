package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.view.GraphicalView;
import javafx.scene.control.Label;

/**
 * The interface represents different states in the application.
 * States define the behavior of the application based on user input and current conditions.
 */
public interface State {
    /**
     * Handles user input based on the current state
     * @param context       the context managing the state transitions
     * @param input         the user input
     * @param graphicalView the graphical view associated with the state
     */
    void handleInput(Context context, String input, GraphicalView graphicalView);

    /**
     * Gets the graphical view associated with the state
     * @return the graphical view
     */
    GraphicalView getGraphicalView();

    /**
     * Gets the currently selected courier in the state
     * @return the selected courier
     */
    Courier getSelectedCourier();

    /**
     * Gets the label providing information in the graphical view
     * @return the information label
     */
    Label getlbInfos();
}
