package com.malveillance.uberif.controller;

import com.malveillance.uberif.model.CityMap;

import java.util.Stack;

public class Context {
    private State currentState;
    private Stack<State> previousStates;

    public Context(CityMap cityMap) {
        this.currentState = new InitialState(cityMap);
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
