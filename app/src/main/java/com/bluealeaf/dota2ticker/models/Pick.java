package com.bluealeaf.dota2ticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class Pick {

    @SerializedName("hero_id")
    @Expose
    private long heroId;

    public Pick(){
        heroId = 0 ;
    }

    /**
     *
     * @return
     * The heroId
     */
    public long getHeroId() {
        return heroId;
    }

    /**
     *
     * @param heroId
     * The hero_id
     */
    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

}
