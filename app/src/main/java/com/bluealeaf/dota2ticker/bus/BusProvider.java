package com.bluealeaf.dota2ticker.bus;

import android.app.Application;
import android.util.Log;

import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.GetMatchesEvent;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by samidh on 4/1/15.
 */
public class BusProvider extends Application {
    private static final Bus BUS = new Bus();
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final String tag = BusProvider.class.getName();

    public static Bus getInstance() {
        return BUS;
    }

    public  static OkHttpClient getClientInstance(){
        return okHttpClient;
    }


    private GetMatchesEvent mGetMatchesEvent;

    @Override
    public void onCreate() {
        super.onCreate();
        mGetMatchesEvent = new GetMatchesEvent();
        Log.d("BusProvider-onCreate", "here");
        BusProvider.getInstance().register(mGetMatchesEvent);

        //create cache directory
        BusProvider.getClientInstance()
                .setConnectTimeout(OkHttpClientConst.connectionTimeout, TimeUnit.MILLISECONDS);

        try {

            Cache cache = new Cache(getApplicationContext().getCacheDir().getAbsoluteFile(), OkHttpClientConst.SIZE);

            BusProvider.getClientInstance().setCache(cache);
        }
        catch(IOException ex){
            Log.d(tag, Errors.Cache_IO_Error);
        }


    }
}
