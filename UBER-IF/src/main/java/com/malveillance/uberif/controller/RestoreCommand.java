package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import com.malveillance.uberif.xml.XMLserializer;
import javafx.util.Pair;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class RestoreCommand implements Command {
    private GraphicalView graphicalView;
    private Context context;

    private CityMapController cityMapController;

    private XMLserializer xmlSerializer;

    public RestoreCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    @Override
    public void execute() {

        System.out.println("Restore click");
        String nameInput = graphicalView.showDialogBoxInput("Enter the input file name", "Input file's name", "Enter the input file's name : ");
        System.out.println(graphicalView.doesResourceExist(nameInput + ".uberif.xml"));
        if (!nameInput.isEmpty() && graphicalView.doesResourceExist(nameInput + ".uberif.xml")) {

            CityMap newMap = cityMapController.loadNewCityMap(nameInput + ".uberif.xml", true);
            graphicalView.getCityMap().merge(newMap);

            for (Courier courier : graphicalView.getCityMap().getListCourier()) {

                if (!graphicalView.getChoiceCourier().getItems().contains(courier.getName()) && !courier.getName().isEmpty()) {
                    if (graphicalView.getNbCouriers() == 0) {
                        graphicalView.getMinusBtn().getStyleClass().remove("grey-state");
                        graphicalView.getMinusBtn().getStyleClass().add("blue-state");
                    }

                    graphicalView.setNbCouriers(graphicalView.getNbCouriers() + 1);
                    graphicalView.getNbCourierLb().setText(String.valueOf(graphicalView.getNbCouriers()));
                    graphicalView.getChoiceCourier().getItems().add(courier.getName());

                    graphicalView.getChoiceCourier().getSelectionModel().select(graphicalView.getChoiceCourier().getItems().size() - 1);
                    graphicalView.setSelectedCourier(new Pair<>(courier, graphicalView.getCityMap().getCourierDotMap().get(courier)));
                }

            }
        } else {
            graphicalView.showDialogWarningError("Error", "No output file found", "File : " + nameInput + ".uberif.xml");
        }


//    @Override
//    public void undo() {
//        if (previousState != null) {
//            // Restore the previous state
//
//        }
//    }
    }
}

