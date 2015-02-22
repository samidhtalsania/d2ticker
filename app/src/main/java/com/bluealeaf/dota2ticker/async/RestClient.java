package com.bluealeaf.dota2ticker.async;

/**
 * Created by samidh on 4/1/15.
 */

import android.util.Log;

import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Endpoints;
import com.bluealeaf.dota2ticker.events.PassMatchListFromNetEvent;
import com.bluealeaf.dota2ticker.models.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static final String tag = RestClient.class.getName();

    public static void getMatchesList(long id){
//        Log.d(tag, "getMatchesList");
        Gson gson = new GsonBuilder()
                    .create();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Endpoints.GET_MATCHES_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(BusProvider.getClientInstance()))
                .build();

        d2ticker ticker = restAdapter.create(d2ticker.class);

        ticker.getMatchesList(id, new Callback<Api>() {
            @Override
            public void success(Api api, Response response) {
                //pass an event to main activity on success
                Log.d(tag,"getMatchesList-S");
                Api temp = api;
                BusProvider.getBusInstance().post(new PassMatchListFromNetEvent(temp));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(tag,"getMatchesList-F");
                //pass an event to main activity on failure
                if(error.getKind() == RetrofitError.Kind.NETWORK){
                    BusProvider.getBusInstance().post(new PassMatchListFromNetEvent("No network found. Please connect to internet."));
                }
                else if(error.getKind() == RetrofitError.Kind.HTTP){
                    BusProvider.getBusInstance().post(new PassMatchListFromNetEvent("Unable to connect to server. Please try again later."));
                }
                else if(error.getKind() == RetrofitError.Kind.CONVERSION){
                    BusProvider.getBusInstance().post(new PassMatchListFromNetEvent("Unable to convert."));
                }
                else if(error.getKind() == RetrofitError.Kind.UNEXPECTED){
                    BusProvider.getBusInstance().post(new PassMatchListFromNetEvent("Unexpected error."));
                }
            }
        });



    }
}
