package com.bluealeaf.dota2ticker.bus;

import android.app.Application;
import android.util.Log;

import com.bluealeaf.dota2ticker.events.GetMatchesEvent;
import com.squareup.otto.Bus;

/**
 * Created by samidh on 4/1/15.
 */
public class BusProvider extends Application {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }


    private GetMatchesEvent mGetMatchesEvent;

    @Override
    public void onCreate() {
        super.onCreate();
        mGetMatchesEvent = new GetMatchesEvent(0);
        Log.d("BusProvider-onCreate", "here");
        BusProvider.getInstance().register(mGetMatchesEvent);
        BusProvider.getInstance().register(this);
    }
}
