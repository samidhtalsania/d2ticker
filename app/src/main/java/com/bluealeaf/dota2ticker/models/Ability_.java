package com.bluealeaf.dota2ticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class Ability_ {

    @SerializedName("ability_id")
    @Expose
    private long abilityId;
    @SerializedName("ability_level")
    @Expose
    private long abilityLevel;

    public Ability_(){
        abilityId = 0 ;
        abilityLevel = 0 ;
    }

    /**
     *
     * @return
     * The abilityId
     */
    public long getAbilityId() {
        return abilityId;
    }

    /**
     *
     * @param abilityId
     * The ability_id
     */
    public void setAbilityId(long abilityId) {
        this.abilityId = abilityId;
    }

    /**
     *
     * @return
     * The abilityLevel
     */
    public long getAbilityLevel() {
        return abilityLevel;
    }

    /**
     *
     * @param abilityLevel
     * The ability_level
     */
    public void setAbilityLevel(long abilityLevel) {
        this.abilityLevel = abilityLevel;
    }

}

