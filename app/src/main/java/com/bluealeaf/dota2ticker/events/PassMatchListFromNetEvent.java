package com.bluealeaf.dota2ticker.events;

/**
 * Created by samidh on 5/1/15.
 */

import com.bluealeaf.dota2ticker.models.Api;
import com.bluealeaf.dota2ticker.models.Match;

import java.util.ArrayList;
import java.util.List;

public class PassMatchListFromNetEvent {
    private Api api;
    private String message;



    public PassMatchListFromNetEvent(Api api){
        this.api = api;
        this.message = "Updated";
    }

    public PassMatchListFromNetEvent(String message){
        this.message = message;
        api = new Api();
        api.setMatches(new ArrayList<Match>());
        api.setTotal(0);
        api.setSuccess(false);
    }

    public List<Match> getMatchList(){
        return api.getMatches();
    }


    public String getMessage() {
        return message;
    }

}
