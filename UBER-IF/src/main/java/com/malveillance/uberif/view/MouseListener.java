package com.malveillance.uberif.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseListener implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        System.out.println("Mouse clicked at (" + event.getX() + ", " + event.getY() + ")");
    }
}
