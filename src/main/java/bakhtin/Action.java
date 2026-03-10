package bakhtin;

import bakhtin.exceptions.IllegalActionException;

public enum Action {
    ANSWER, RESTART, EXIT;

    public static Action parse(String actionStr) {
        if (actionStr != null) {
            try {
                return Action.valueOf(actionStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalActionException("Action not found for: " + actionStr);
            }
        }
        throw new IllegalActionException("Action not found for: " + actionStr);
    }
}