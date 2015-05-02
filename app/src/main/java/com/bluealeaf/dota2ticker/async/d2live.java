package com.bluealeaf.dota2ticker.async;


import com.bluealeaf.dota2ticker.models.LiveMatch;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by samidh on 2/5/15.
 */
public interface d2live {
    @GET("/match/live")
    void getLiveMatchesList(Callback<LiveMatch> result);
}
