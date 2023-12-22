package com.malveillance.uberif.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * The `KeyboardHandler` class handles key events in the graphical view.
 */
public class KeyboardHandler implements EventHandler<KeyEvent> {
    private GraphicalView graphicalView;

    /**
     * Constructs a new `KeyboardHandler`.
     *
     * @param graphicalView the graphical view to associate with the handler
     */
    public KeyboardHandler(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    /**
     * Handles key events.
     *
     * @param event the key event
     */
    @Override
    public void handle(KeyEvent event) {
        KeyCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlR = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlO = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);

        if (event.getCode() == KeyCode.ENTER) {
            graphicalView.onOptimizeBtnClick();
        } else if (ctrlS.match(event)) {
            graphicalView.onSaveBtnClick();
        } else if (ctrlR.match(event)) {
            graphicalView.onRestoreBtnClick();
        } else if (ctrlP.match(event)) {
            graphicalView.undo();
        } else if (ctrlO.match(event)) {
            System.out.println("Lolz");
            graphicalView.redo();
        } else if (event.getCode() == KeyCode.PLUS) {
            graphicalView.onPlusBtnClick();
        } else if (event.getCode() == KeyCode.MINUS) {
            graphicalView.onMinusBtnClick();
        } else {
            System.out.println("Key pressed: " + event.getText());
        }
    }
}
