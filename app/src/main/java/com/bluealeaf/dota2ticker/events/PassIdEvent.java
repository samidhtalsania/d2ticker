package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 5/1/15.
 */
public class PassIdEvent {
    private int id;
    public PassIdEvent(int id){
        this.id = id;
    }
    public PassIdEvent(){

    }


    public int getId(){
        return id;
    }


}
