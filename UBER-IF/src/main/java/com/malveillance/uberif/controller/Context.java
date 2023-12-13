package com.malveillance.uberif.controller;

import java.util.Stack;

public class Context {
    private State currentState;
    private Stack<State> previousStates;

    public Context() {
        this.currentState = new InitialState();
        this.previousStates = null;// Default state
    }

    public void setState(State state) {
        this.previousStates.add(currentState);
        this.currentState = state;
    }

    public void handleInput(String input) {

        currentState.handleInput(this, input);
    }
}
