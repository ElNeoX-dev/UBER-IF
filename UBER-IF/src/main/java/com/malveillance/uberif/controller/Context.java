package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.view.GraphicalView;

import java.util.Stack;

/**
 * The class represents a context for managing states and handling user input.
 */
public class Context {
    /**
     * Represents the current state.
     */
    private State currentState;

    /**
     * Represents the previous states.
     */
    private Stack<State> previousStates;

    /**
     * Represents the undone states.
     */
    private Stack<State> undoneStates;

    /**
     * Constructs a new Context
     * @param graphicalView the graphical view associated with the context
     */
    public Context(GraphicalView graphicalView) {
        this.currentState = new InitialState(graphicalView);
        this.previousStates = new Stack<>();
        this.undoneStates = new Stack<>();
    }

    /**
     * Sets the state of the context
     * @param state the new state
     */
    public void setState(State state) {
        if (currentState != null) {
            this.previousStates.push(currentState);
        }
        this.currentState = state;
        this.undoneStates.clear();
    }

    /**
     * Gets the current state of the context
     * @return the current state
     */
    public State getState() {
        return currentState;
    }

    /**
     * Undoes the last state change.
     */
    public void undo() {
        if (!previousStates.empty()) {
            this.undoneStates.push(currentState);
            this.currentState = previousStates.pop();
        }
    }

    /**
     * Redoes the last undone state change.
     */
    public void redo() {
        if (!undoneStates.empty()) {
            this.previousStates.push(currentState);
            this.currentState = undoneStates.pop();
        }
    }

    /**
     * Handles user input based on the current state
     * @param input         the user input
     * @param graphicalView the graphical view associated with the context
     */
    public void handleInput(String input, GraphicalView graphicalView) {
        currentState.handleInput(this, input, graphicalView);
    }
}
