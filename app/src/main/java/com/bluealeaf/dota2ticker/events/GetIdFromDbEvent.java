package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 5/1/15.
 */
public class GetIdFromDbEvent {
    private int mode;

    public GetIdFromDbEvent(int mode){
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
