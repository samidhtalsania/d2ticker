package com.bluealeaf.dota2ticker.exceptions;

/**
 * Created by samidh on 12/1/15.
 */
public class CannotConnectToServerException extends Exception {

    private String message ;

    public CannotConnectToServerException(){
        super();
    }

    public CannotConnectToServerException(String message){
        super(message);
        this.message = message;
    }

    public CannotConnectToServerException(Throwable cause){
        super(cause);
    }

    @Override
    public String toString(){
        return message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
