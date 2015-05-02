package com.bluealeaf.dota2ticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samidh on 2/5/15.
 */
public class Radiant {

    @Expose
    private long score;
    @SerializedName("tower_state")
    @Expose
    private long towerState;
    @SerializedName("barracks_state")
    @Expose
    private long barracksState;
    @Expose
    private List<Pick> picks = new ArrayList<Pick>();
    @Expose
    private List<Ban> bans = new ArrayList<Ban>();
    @Expose
    private List<Player_> players = new ArrayList<Player_>();
    @Expose
    private List<Ability> abilities = new ArrayList<Ability>();

    public Radiant(){
        score = 0 ;
        towerState = 0 ;
        barracksState = 0;
        picks.add(new Pick());
        bans.add(new Ban());
        players.add(new Player_());
        abilities.add(new Ability());
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
    public List<Pick> getPicks() {
        return picks;
    }

    /**
     *
     * @param picks
     * The picks
     */
    public void setPicks(List<Pick> picks) {
        this.picks = picks;
    }

    /**
     *
     * @return
     * The bans
     */
    public List<Ban> getBans() {
        return bans;
    }

    /**
     *
     * @param bans
     * The bans
     */
    public void setBans(List<Ban> bans) {
        this.bans = bans;
    }

    /**
     *
     * @return
     * The players
     */
    public List<Player_> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     * The players
     */
    public void setPlayers(List<Player_> players) {
        this.players = players;
    }

    /**
     *
     * @return
     * The abilities
     */
    public List<Ability> getAbilities() {
        return abilities;
    }

    /**
     *
     * @param abilities
     * The abilities
     */
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

}
