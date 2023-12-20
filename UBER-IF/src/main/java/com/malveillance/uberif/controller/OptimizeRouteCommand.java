package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.*;
import com.malveillance.uberif.model.service.AlgoService;
import com.malveillance.uberif.view.GraphicalView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


public class OptimizeRouteCommand implements Command {
    private GraphicalView graphicalView;
    private Context context;

    public OptimizeRouteCommand(GraphicalView graphicalView, Context context) {
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



//    @Override
//    public void undo() {
//        if (previousState != null) {
//            // Restore the previous state
//
//        }
//    }
}

