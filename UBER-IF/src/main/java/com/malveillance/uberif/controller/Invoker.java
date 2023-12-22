package com.malveillance.uberif.controller;

/**
 * The class represents an invoker.
 */
public class Invoker {

    /**
     * The command.
     */
    private Command command;

    /**
     * Sets the command.
     * @param command the command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the command.
     */
    public void executeCommand() {
        command.execute();
    }

}

