package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 2/3/15.
 */
public class RestErrorEvent {
    private String message;

    public RestErrorEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
