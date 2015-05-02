package com.bluealeaf.dota2ticker.async;

import android.util.Log;

import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Endpoints;
import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.events.PassLiveMatchListFromNetEvent;
import com.bluealeaf.dota2ticker.models.LiveMatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by samidh on 2/5/15.
 */
public class LiveRestClient {

    public static final String tag = LiveRestClient.class.getName();

    public static void getLiveMatchesList(){

        Gson gson = new GsonBuilder()
                .create();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Endpoints.GET_MATCHES_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(BusProvider.getClientInstance()))
                .build();

        d2live live_matches = restAdapter.create(d2live.class);

        live_matches.getLiveMatchesList(new Callback<LiveMatch>() {
            @Override
            public void success(LiveMatch result, Response response) {
                Log.d(tag, "getLiveMatchesList-S");
                LiveMatch temp = result;
                BusProvider.getBusInstance().post(new PassLiveMatchListFromNetEvent(temp));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(tag,"getLiveMatchesList-F");
                //pass an event to main activity on failure
                if(error.getKind() == RetrofitError.Kind.NETWORK){
                    Log.d(tag,Errors.Retrofit_NETWORK);
                }
                else if(error.getKind() == RetrofitError.Kind.HTTP){
                    Log.d(tag,Errors.Retrofit_HTTP);
                }
                else if(error.getKind() == RetrofitError.Kind.CONVERSION){
                    Log.d(tag,Errors.Retrofit_CONVERSION);
                }
                else if(error.getKind() == RetrofitError.Kind.UNEXPECTED){
                    Log.d(tag,Errors.Retrofit_UNEXPECTED);
                }
            }
        });

    }
}
