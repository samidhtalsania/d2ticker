package com.bluealeaf.dota2ticker.async;

/**
 * Created by samidh on 4/1/15.
 */

import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Endpoints;
import com.bluealeaf.dota2ticker.events.PassMatchListEvent;
import com.bluealeaf.dota2ticker.models.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class RestClient {


    public static void getMatchesList(int id){

        Gson gson = new GsonBuilder()
                    .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Endpoints.GET_MATCHES_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        d2ticker ticker = restAdapter.create(d2ticker.class);
        ticker.getMatchesList(id, new Callback<Api>() {
            @Override
            public void success(Api api, Response response) {
                //pass an event to main activity on success
                Api temp = api;
                BusProvider.getInstance().post(new PassMatchListEvent(temp));
            }

            @Override
            public void failure(RetrofitError error) {
                //pass an event to main activity on failure
            }
        });

    }
}
