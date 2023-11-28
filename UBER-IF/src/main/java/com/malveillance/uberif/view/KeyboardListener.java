package com.malveillance.uberif.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardListener implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
        System.out.println("Key pressed: " + event.getText());
    }
}
