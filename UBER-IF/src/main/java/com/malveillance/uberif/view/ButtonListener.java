package com.malveillance.uberif.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonListener implements EventHandler<ActionEvent> {

    public ButtonListener() {
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Button clicked!");
    }
}
