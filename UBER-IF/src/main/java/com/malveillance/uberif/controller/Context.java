package com.malveillance.uberif.controller;

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

    /**
     * Constructs a new Context.
     */
    public Context() {
        this.currentState = new InitialState();
        this.previousStates = null;// Default state
    }

    /**
     * sets the state.
     * @param state the state
     */
    public void setState(State state) {
        this.previousStates.add(currentState);
        this.currentState = state;
    }

    /**
     * Handles the input.
     * @param input the input command
     */
    public void handleInput(String input) {

        currentState.handleInput(this, input);
    }
}
