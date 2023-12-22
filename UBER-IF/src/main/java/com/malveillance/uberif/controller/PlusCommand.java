package com.malveillance.uberif.controller;

import com.malveillance.uberif.view.GraphicalView;


public class PlusCommand implements Command {
    private GraphicalView graphicalView;
    private Context context;

    public PlusCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

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

