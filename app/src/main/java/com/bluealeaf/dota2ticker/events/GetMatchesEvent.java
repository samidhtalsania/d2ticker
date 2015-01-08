package com.bluealeaf.dota2ticker.events;

import android.util.Log;

import com.bluealeaf.dota2ticker.async.RestClient;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

/**
 * Created by samidh on 4/1/15.
 */
public class GetMatchesEvent {

    private int id;

    public GetMatchesEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }




    @Subscribe
    public void OnRequestForId(GetIdEvent event){
        //do db stuff
        //Get the last ID
        id = 1 ;
        Log.d("OnRequestForId", "Here");
        BusProvider.getInstance().post(new PassIdEvent());

    }

    @Produce
    public PassIdEvent producePassIdEvent(){
        return new PassIdEvent(id);
    }


    @Subscribe
    public void onReceiveId(PassIdEvent event){
        int id = event.getId();
        RestClient.getMatchesList(id);
    }
}
