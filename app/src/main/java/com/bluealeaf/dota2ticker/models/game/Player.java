package com.bluealeaf.dota2ticker.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class Player {

    @SerializedName("account_id")
    @Expose
    private long accountId;
    @Expose
    private String name;
    @SerializedName("hero_id")
    @Expose
    private long heroId;
    @Expose
    private long team;


    public Player(){
        accountId = 0 ;
        name = "0";
        heroId = 0 ;
        team = 0 ;
    }
    /**
     *
     * @return
     * The accountId
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     * The account_id
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     *
     * @return
     * The team
     */
    public long getTeam() {
        return team;
    }

    /**
     *
     * @param team
     * The team
     */
    public void setTeam(long team) {
        this.team = team;
    }

}
