package com.malveillance.uberif.controller;

import com.malveillance.uberif.view.GraphicalView;

/**
 * The class represents a command for adding a courier to the application.
 * It is associated with the "Plus" button in the graphical user interface.
 */
public class PlusCommand implements Command {
    /**
     * The graphical view associated with the command.
     */
    private GraphicalView graphicalView;
    /**
     * The context managing the state transitions.
     */
    private Context context;

    /**
     * Constructs a new PlusCommand
     * @param graphicalView the graphical view associated with the command
     * @param context       the context managing the state transitions
     */
    public PlusCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    /**
     * Executes the addition of a courier to the application.
     * It prompts the user to enter the courier's name and adds the courier to the graphical view.
     * The context is then notified of the action.
     */
    @Override
    public void execute() {
        // CityMap previousCityMap = cityMap.deepCopy();

        String nameCourier = graphicalView.showDialogBoxInput("Enter the courier's name", "Courier's name", "Enter the courier's name : ");

        if (nameCourier != null && !nameCourier.isEmpty()) {
            graphicalView.addCourier(nameCourier);
        }
        context.handleInput("plus", graphicalView);
    }
}
