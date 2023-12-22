package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.view.GraphicalView;

import java.util.Stack;

/**
 * The class represents a context.
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
    private Stack<State> undoneStates;

    /**
     * Constructs a new Context.
     */
    public Context(GraphicalView graphicalView) {
        this.currentState = new InitialState(graphicalView);
        this.previousStates = new Stack<>();
        this.undoneStates = new Stack<>();
    }

    /**
     * sets the state.
     * @param state the state
     */
    public void setState(State state) {
        if (currentState != null) {
            this.previousStates.push(currentState);
        }
        this.currentState = state;
        this.undoneStates.clear();
    }

    public State getState() {
        return currentState;
    }

    public void undo() {
        if (!previousStates.empty()) {
            this.undoneStates.push(currentState);
            this.currentState = previousStates.pop();
        }
    }

    public void redo() {
        if (!undoneStates.empty()) {
            this.previousStates.push(currentState);
            this.currentState = undoneStates.pop();
        }
    }

    public void handleInput(String input, GraphicalView graphicalView) {
        currentState.handleInput(this, input, graphicalView);
    }
}
