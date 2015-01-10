package com.bluealeaf.dota2ticker.events;

import android.util.Log;

import com.bluealeaf.dota2ticker.async.RestClient;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.squareup.otto.Subscribe;

/**
 * Created by samidh on 4/1/15.
 */
public class GetMatchesEvent {

    private int id;
    private static final String tag = GetMatchesEvent.class.getName();

    public GetMatchesEvent() { }

    @Subscribe
    public void OnRequestForId(GetIdFromDbEvent event){
        //do db stuff
        //Get the last ID
        id = 1 ;

        Log.d(tag, "Here");
        BusProvider.getInstance().post(new PassIdEvent(id));
    }


    @Subscribe
    public void onReceiveId(PassIdEvent event){
        int id = event.getId();
        RestClient.getMatchesList(id);
    }
}
