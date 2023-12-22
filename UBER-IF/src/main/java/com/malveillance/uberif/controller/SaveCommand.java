package com.malveillance.uberif.controller;

import com.malveillance.uberif.view.GraphicalView;
import com.malveillance.uberif.xml.XMLserializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class SaveCommand implements Command {
    private GraphicalView graphicalView;
    private Context context;

    private XMLserializer xmlSerializer;

    public SaveCommand(GraphicalView graphicalView, Context context) {
        this.graphicalView = graphicalView;
        this.context = context;
    }

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



//    @Override
//    public void undo() {
//        if (previousState != null) {
//            // Restore the previous state
//
//        }
//    }
}

