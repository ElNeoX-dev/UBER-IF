package com.malveillance.uberif.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * The class represents a mouse listener.
 */
public class MouseListener implements EventHandler<MouseEvent> {

    /**
     * Constructs a new MouseListener.
     */
    @Override
    public void handle(MouseEvent event) {
        System.out.println("Mouse clicked at (" + event.getX() + ", " + event.getY() + ")");
    }
}
