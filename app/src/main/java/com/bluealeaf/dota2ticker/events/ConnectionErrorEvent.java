package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 12/1/15.
 */
public class ConnectionErrorEvent {
    private String message = "";

    public ConnectionErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
