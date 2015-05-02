package com.bluealeaf.dota2ticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class Scoreboard {

    @Expose
    private double duration;
    @SerializedName("roshan_respawn_timer")
    @Expose
    private long roshanRespawnTimer;
    @Expose
    private Radiant radiant;
    @Expose
    private Dire dire;

    public Scoreboard(){
        duration = 0 ;
        roshanRespawnTimer = 0 ;
        radiant = new Radiant();
        dire = new Dire();
    }

    /**
     *
     * @return
     * The duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     * The roshanRespawnTimer
     */
    public long getRoshanRespawnTimer() {
        return roshanRespawnTimer;
    }

    /**
     *
     * @param roshanRespawnTimer
     * The roshan_respawn_timer
     */
    public void setRoshanRespawnTimer(long roshanRespawnTimer) {
        this.roshanRespawnTimer = roshanRespawnTimer;
    }

    /**
     *
     * @return
     * The radiant
     */
    public Radiant getRadiant() {
        return radiant;
    }

    /**
     *
     * @param radiant
     * The radiant
     */
    public void setRadiant(Radiant radiant) {
        this.radiant = radiant;
    }

    /**
     *
     * @return
     * The dire
     */
    public Dire getDire() {
        return dire;
    }

    /**
     *
     * @param dire
     * The dire
     */
    public void setDire(Dire dire) {
        this.dire = dire;
    }

}
