package com.bluealeaf.dota2ticker.bus;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.GetMatchesEvent;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by samidh on 4/1/15.
 */
public class BusProvider extends Application {

    private static final String tag = BusProvider.class.getName();

    //initialize event bus
    private static final Bus BUS = new Bus();

    //initialize http client
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    //initialize database session object
    private static DaoSession daoSession;


    //Return a singleton instance of Bus
    public static Bus getBusInstance() {
        return BUS;
    }

    //Return a singleton instance of Http client
    public  static OkHttpClient getClientInstance(){
        return okHttpClient;
    }

    //Return a singleton instance of DAO Session
    public  static  DaoSession getDaoSessionInstance(){ return daoSession ;}


    private GetMatchesEvent mGetMatchesEvent;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDatabase();

        setupCache();

        //Register GetMatchesEvent class to enabe it to receive events.
        //All subscriber methods are written in this class
        mGetMatchesEvent = new GetMatchesEvent();
        BusProvider.getBusInstance().register(mGetMatchesEvent);
    }

    private void setupCache(){

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

    private void setupDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "d2ticker-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
