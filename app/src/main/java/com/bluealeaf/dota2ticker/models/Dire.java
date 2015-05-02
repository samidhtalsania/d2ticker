package com.bluealeaf.dota2ticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samidh on 2/5/15.
 */
public class Dire {

    @Expose
    private long score;
    @SerializedName("tower_state")
    @Expose
    private long towerState;
    @SerializedName("barracks_state")
    @Expose
    private long barracksState;
    @Expose
    private List<Pick_> picks = new ArrayList<Pick_>();
    @Expose
    private List<Ban_> bans = new ArrayList<Ban_>();
    @Expose
    private List<Player__> players = new ArrayList<Player__>();
    @Expose
    private List<Ability_> abilities = new ArrayList<Ability_>();


    public Dire(){
        score = 0;
        towerState = 0;
        barracksState = 0 ;

    }

    /**
     *
     * @return
     * The score
     */
    public long getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     *
     * @return
     * The towerState
     */
    public long getTowerState() {
        return towerState;
    }

    /**
     *
     * @param towerState
     * The tower_state
     */
    public void setTowerState(long towerState) {
        this.towerState = towerState;
    }

    /**
     *
     * @return
     * The barracksState
     */
    public long getBarracksState() {
        return barracksState;
    }

    /**
     *
     * @param barracksState
     * The barracks_state
     */
    public void setBarracksState(long barracksState) {
        this.barracksState = barracksState;
    }

    /**
     *
     * @return
     * The picks
     */
    public List<Pick_> getPicks() {
        return picks;
    }

    /**
     *
     * @param picks
     * The picks
     */
    public void setPicks(List<Pick_> picks) {
        this.picks = picks;
    }

    /**
     *
     * @return
     * The bans
     */
    public List<Ban_> getBans() {
        return bans;
    }

    /**
     *
     * @param bans
     * The bans
     */
    public void setBans(List<Ban_> bans) {
        this.bans = bans;
    }

    /**
     *
     * @return
     * The players
     */
    public List<Player__> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     * The players
     */
    public void setPlayers(List<Player__> players) {
        this.players = players;
    }

    /**
     *
     * @return
     * The abilities
     */
    public List<Ability_> getAbilities() {
        return abilities;
    }

    /**
     *
     * @param abilities
     * The abilities
     */
    public void setAbilities(List<Ability_> abilities) {
        this.abilities = abilities;
    }

}
