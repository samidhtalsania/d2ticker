package com.bluealeaf.dota2ticker.events;

import com.bluealeaf.dota2ticker.models.game.Game;
import com.bluealeaf.dota2ticker.models.game.LiveMatch;

import java.util.ArrayList;

/**
 * Created by samidh on 2/5/15.
 */
public class PassLiveMatchListFromNetEvent {

    private LiveMatch result;


    public PassLiveMatchListFromNetEvent(LiveMatch result){
        this.result = result;
    }


    public ArrayList<Game> getMatchList(){
        return result.getResult().getGames();
    }



}
