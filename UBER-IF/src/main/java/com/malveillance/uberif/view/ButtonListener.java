package com.malveillance.uberif.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The class represents a button listener.
 */
public class ButtonListener implements EventHandler<ActionEvent> {

    /**
     * Constructs a new ButtonListener.
     */
    public ButtonListener() {
    }

    /**
     * Handles the button click event.
     * @param event the button click event
     */
    @Override
    public void handle(ActionEvent event) {
        System.out.println("Button clicked!");
    }
}
