package com.bluealeaf.dota2ticker.async;


import com.bluealeaf.dota2ticker.models.Team_img;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by samidh on 5/5/15.
 */
public interface teamImage {

    @GET("/match/team/image/{id}")
    void getTeamLogo(@Path("id") long id,Callback<Team_img> image);
}
