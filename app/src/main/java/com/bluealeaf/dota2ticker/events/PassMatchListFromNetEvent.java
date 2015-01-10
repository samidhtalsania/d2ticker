package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 5/1/15.
 */

import com.bluealeaf.dota2ticker.models.Api;
import com.bluealeaf.dota2ticker.models.Match;

import java.util.List;

public class PassMatchListFromNetEvent {
    private Api api;
    public PassMatchListFromNetEvent(Api api){
        this.api = api;
    }

    public List<Match> getMatchList(){
        return api.getMatches();
    }

    public boolean getStatus(){
        return api.isSuccess();
    }

    public int getTotal(){
        return api.getTotal();
    }
}
