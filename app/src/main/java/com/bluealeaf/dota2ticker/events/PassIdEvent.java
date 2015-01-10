package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 5/1/15.
 */
public class PassIdEvent {
    private long id;
    public PassIdEvent(long id){
        this.id = id;
    }
    public PassIdEvent(){

    }


    public long getId(){
        return id;
    }


}
