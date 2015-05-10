package com.bluealeaf.dota2ticker.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class DireTeam {

    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("team_id")
    @Expose
    private long teamId;
    @SerializedName("team_logo")
    @Expose
    private long teamLogo;
    @Expose
    private boolean complete;

    public DireTeam(){
        teamName = "0";
        teamId = 0 ;
        teamLogo = 0 ;
        complete = false;
    }

    /**
     *
     * @return
     * The teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     *
     * @param teamName
     * The team_name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     *
     * @return
     * The teamId
     */
    public long getTeamId() {
        return teamId;
    }

    /**
     *
     * @param teamId
     * The team_id
     */
    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    /**
     *
     * @return
     * The teamLogo
     */
    public long getTeamLogo() {
        return teamLogo;
    }

    /**
     *
     * @param teamLogo
     * The team_logo
     */
    public void setTeamLogo(long teamLogo) {
        this.teamLogo = teamLogo;
    }

    /**
     *
     * @return
     * The complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     *
     * @param complete
     * The complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
