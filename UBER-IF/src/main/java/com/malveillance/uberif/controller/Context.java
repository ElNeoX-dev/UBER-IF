package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;
import com.malveillance.uberif.view.GraphicalView;

import java.util.Stack;

public class Context {
    private State currentState;
    private Stack<State> previousStates;
    private Stack<State> undoneStates;

    public Context(GraphicalView graphicalView) {
        this.currentState = new InitialState(graphicalView);
        this.previousStates = new Stack<>();
        this.undoneStates = new Stack<>();
    }

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

    public void handleInput(String input) {
        currentState.handleInput(this, input);
    }
}
