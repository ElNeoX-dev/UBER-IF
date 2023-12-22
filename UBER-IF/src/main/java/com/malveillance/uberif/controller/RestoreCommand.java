package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.view.GraphicalView;
import com.malveillance.uberif.xml.XMLserializer;
import javafx.util.Pair;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * The class represents a command for restoring a CityMap from an XML file.
 * It is associated with the "Restore" button in the graphical user interface.
 */
public class RestoreCommand implements Command {
    /**
     * The graphical view associated with the command.
     */
    private GraphicalView graphicalView;
    /**
     * The context managing the state transitions.
     */
    private Context context;

    /**
     * The controller managing the CityMap.
     */
    private CityMapController cityMapController;

    /**
     * The XML serializer used to deserialize the CityMap.
     */
    private XMLserializer xmlSerializer;

    /**
     * Constructs a new RestoreCommand
     * @param graphicalView   the graphical view associated with the command
     * @param context         the context managing the state transitions
     */
    public RestoreCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    /**
     * Executes the restoration of a CityMap from an XML file.
     * It prompts the user to select an XML file, loads the CityMap, and updates the graphical view accordingly.
     * The context is then notified of the action.
     */
    @Override
    public void execute() {
        System.out.println("Restore click");
        String path = graphicalView.showFileChooser();
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

            graphicalView.onOptimizeBtnClick();
        } else {
            graphicalView.showDialogWarningError("Error", "No input file found", "File: " + path);
        }
    }
}
