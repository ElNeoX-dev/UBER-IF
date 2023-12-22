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
        String path = graphicalView.showFileChooser() ;
        System.out.println(path);
        if (!path.isEmpty()) {

            CityMap newMap = cityMapController.loadNewCityMap(path, true);
            graphicalView.getCityMap().merge(newMap);

            for (Courier courier : graphicalView.getCityMap().getListCourier()) {

                if (!graphicalView.getChoiceCourier().getItems().contains(courier.getName()) && !courier.getName().isEmpty()) {
                    if (graphicalView.getNbCouriers() == 0) {
                        graphicalView.getMinusBtn().getStyleClass().remove("grey-state");
                        graphicalView.getMinusBtn().getStyleClass().add("blue-state");
                    }

                    int nbCouriers = graphicalView.getNbCouriers() + 1;
                    graphicalView.setNbCouriers(nbCouriers);
                    graphicalView.getNbCourierLb().setText(String.valueOf(nbCouriers));
                    graphicalView.getChoiceCourier().getItems().add(courier.getName());

                    graphicalView.getChoiceCourier().getSelectionModel().select(graphicalView.getChoiceCourier().getItems().size() - 1);
                    graphicalView.setSelectedCourier(new Pair<>(courier, graphicalView.getCityMap().getCourierDotMap().get(courier)));
                }

            }

            graphicalView.update(graphicalView.getCityMap(), graphicalView.getCityMap().getNodes());

        } else {
            graphicalView.showDialogWarningError("Error", "No input file found", "File : " + path);
        }

    }
}

