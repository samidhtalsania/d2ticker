package com.bluealeaf.dota2ticker.async;

import com.bluealeaf.dota2ticker.models.Api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by samidh on 5/1/15.
 */
public interface d2ticker {
    @GET("/match/{id}")
    void getMatchesList(@Path("id") int id, Callback<Api> api);
}
