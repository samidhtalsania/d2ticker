package com.bluealeaf.dota2ticker.events;

import com.bluealeaf.dota2ticker.async.RestClient;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.util.MatchConverter;
import com.squareup.otto.Subscribe;

import java.util.List;

import database.MatchDbOperations;
import greendao.Match;

/**
 * Created by samidh on 4/1/15.
 */
public class GetMatchesEvent {

    private long id;
    private static final String tag = GetMatchesEvent.class.getName();

    public GetMatchesEvent() { }

    @Subscribe
    public void OnRequestForId(GetIdFromDbEvent event){

        //do db stuff
        List<Match> matchList = MatchDbOperations.getAllMatches();
        BusProvider.getBusInstance().post(new PassMatchListFromDBEvent(matchList));

        //Get the last ID
        matchList = MatchDbOperations.getMaxId();
        if(matchList.size() == 0){
            id = 0;
        }
        else{
            id = matchList.get(0).getId();
        }


        //Make network call to get latest matches
        BusProvider.getBusInstance().post(new PassIdEvent(id));
    }


    @Subscribe
    public void onReceiveId(PassIdEvent event){
        long id = event.getId();
        RestClient.getMatchesList(id);
    }

    @Subscribe
    public void onReceiveNewMatches(PassMatchListFromNetEvent event){
        //New matches are fetched.
        //Add the into database.
        if(event != null) {
            List<Match> dbMatchList = MatchConverter.netToDB(event.getMatchList());
            MatchDbOperations.insertAll(dbMatchList);

            //Send another event to main ui to update it with the new matches
            BusProvider.getBusInstance().post(new UpdateMatchesEvent(dbMatchList));
        }
    }
}
