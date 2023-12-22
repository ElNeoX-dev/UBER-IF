package com.malveillance.uberif.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The class represents a keyboard listener.
 */
public class KeyboardListener implements EventHandler<KeyEvent> {

    /**
     * Constructs a new KeyboardListener.
     */
    @Override
    public void handle(KeyEvent event) {
        System.out.println("Key pressed: " + event.getText());
    }
}
