package com.bluealeaf.dota2ticker.async;

import android.util.Log;

import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Endpoints;
import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.events.TeamLogoReceivedEvent;
import com.bluealeaf.dota2ticker.models.Team_img;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by samidh on 6/5/15.
 */
public class TeamImageClient {

    public static final String tag = LiveRestClient.class.getName();

    public static void getTeamLogo(long id, final int position, final String team){

        Gson gson = new GsonBuilder()
                .create();


        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Cache-Control","max-age=315360000, public");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Endpoints.GET_MATCHES_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(BusProvider.getClientInstance()))
                .setRequestInterceptor(interceptor)
                .build();

        teamImage image = restAdapter.create(teamImage.class);

        image.getTeamLogo(id,new Callback<Team_img>() {
            @Override
            public void success(Team_img image, Response response) {

                Team_img temp = image;
                BusProvider.getBusInstance().post(new TeamLogoReceivedEvent(temp,position,team));
            }

            @Override
            public void failure(RetrofitError error) {

                //pass an event to main activity on failure
                if(error.getKind() == RetrofitError.Kind.NETWORK){
                    Log.d(tag, Errors.Retrofit_NETWORK);
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
