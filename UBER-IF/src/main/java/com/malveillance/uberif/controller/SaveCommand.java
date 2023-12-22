package com.malveillance.uberif.controller;

import com.malveillance.uberif.view.GraphicalView;
import com.malveillance.uberif.xml.XMLserializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * The {@code SaveCommand} class represents a command for saving the current state of the CityMap to an XML file.
 * It is associated with the "Save" button in the graphical user interface.
 */
public class SaveCommand implements Command {
    /**
     * The graphical view associated with the command.
     */
    private GraphicalView graphicalView;
    /**
     * The context managing the state transitions.
     */
    private Context context;
    /**
     * The XML serializer used to serialize the CityMap.
     */
    private XMLserializer xmlSerializer;

    /**
     * Constructs a new SaveCommand
     * @param graphicalView   the graphical view associated with the command
     * @param context         the context managing the state transitions
     */
    public SaveCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

    /**
     * Executes the saving of the current state of the CityMap to an XML file.
     * It prompts the user to enter the output file name, serializes the CityMap, and notifies the context of the action.
     * In case of errors during serialization, it throws a {@code RuntimeException}.
     */
    @Override
    public void execute() {
        String nameOutput = graphicalView.showDialogBoxInput("Enter the output file name", "Output file's name", "Enter the output file's name : ");

        if (!nameOutput.isEmpty()) {
            try {
                xmlSerializer.serialize(graphicalView.getCityMap().getCourierDotMap().keySet(), graphicalView.getCityMap().getWarehouse(), nameOutput);
            } catch (ParserConfigurationException e) {
                System.out.println("ParserConfigurationException : " + e);
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                System.out.println("TransformerException : " + e);
                throw new RuntimeException(e);
            }
        } else {
            graphicalView.showDialogWarningError("Error", "The file name is empty", "");
        }
        context.handleInput("save", graphicalView);
    }
}
