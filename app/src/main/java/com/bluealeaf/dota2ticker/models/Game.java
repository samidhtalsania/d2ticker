package com.bluealeaf.dota2ticker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samidh on 2/5/15.
 */

public class Game implements Parcelable {

    @Expose
    private List<Player> players = new ArrayList<Player>();
    @SerializedName("radiant_team")
    @Expose
    private RadiantTeam radiantTeam;
    @SerializedName("dire_team")
    @Expose
    private DireTeam direTeam;
    @SerializedName("lobby_id")
    @Expose
    private long lobbyId;
    @SerializedName("match_id")
    @Expose
    private long matchId;
    @Expose
    private long spectators;
    @SerializedName("league_id")
    @Expose
    private long leagueId;
    @SerializedName("stream_delay_s")
    @Expose
    private long streamDelayS;
    @SerializedName("radiant_series_wins")
    @Expose
    private long radiantSeriesWins;
    @SerializedName("dire_series_wins")
    @Expose
    private long direSeriesWins;
    @SerializedName("series_type")
    @Expose
    private long seriesType;
    @SerializedName("league_tier")
    @Expose
    private long leagueTier;
    @Expose
    private Scoreboard scoreboard;

    public Game(){
        scoreboard = new Scoreboard();
        leagueTier = 0 ;
        seriesType = 0;
        direSeriesWins = 0 ;
        radiantSeriesWins = 0 ;
        streamDelayS = 0 ;
        leagueId = 0 ;
        spectators = 0 ;
        matchId = 0 ;
        direTeam = new DireTeam();
        radiantTeam = new RadiantTeam();
        players.add(new Player());
    }

    /**
     *
     * @return
     * The players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     * The players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return
     * The radiantTeam
     */
    public RadiantTeam getRadiantTeam() {
        return radiantTeam;
    }

    /**
     *
     * @param radiantTeam
     * The radiant_team
     */
    public void setRadiantTeam(RadiantTeam radiantTeam) {
        this.radiantTeam = radiantTeam;
    }

    /**
     *
     * @return
     * The direTeam
     */
    public DireTeam getDireTeam() {
        return direTeam;
    }

    /**
     *
     * @param direTeam
     * The dire_team
     */
    public void setDireTeam(DireTeam direTeam) {
        this.direTeam = direTeam;
    }

    /**
     *
     * @return
     * The lobbyId
     */
    public long getLobbyId() {
        return lobbyId;
    }

    /**
     *
     * @param lobbyId
     * The lobby_id
     */
    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    /**
     *
     * @return
     * The matchId
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     *
     * @param matchId
     * The match_id
     */
    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    /**
     *
     * @return
     * The spectators
     */
    public long getSpectators() {
        return spectators;
    }

    /**
     *
     * @param spectators
     * The spectators
     */
    public void setSpectators(long spectators) {
        this.spectators = spectators;
    }

    /**
     *
     * @return
     * The leagueId
     */
    public long getLeagueId() {
        return leagueId;
    }

    /**
     *
     * @param leagueId
     * The league_id
     */
    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }

    /**
     *
     * @return
     * The streamDelayS
     */
    public long getStreamDelayS() {
        return streamDelayS;
    }

    /**
     *
     * @param streamDelayS
     * The stream_delay_s
     */
    public void setStreamDelayS(long streamDelayS) {
        this.streamDelayS = streamDelayS;
    }

    /**
     *
     * @return
     * The radiantSeriesWins
     */
    public long getRadiantSeriesWins() {
        return radiantSeriesWins;
    }

    /**
     *
     * @param radiantSeriesWins
     * The radiant_series_wins
     */
    public void setRadiantSeriesWins(long radiantSeriesWins) {
        this.radiantSeriesWins = radiantSeriesWins;
    }

    /**
     *
     * @return
     * The direSeriesWins
     */
    public long getDireSeriesWins() {
        return direSeriesWins;
    }

    /**
     *
     * @param direSeriesWins
     * The dire_series_wins
     */
    public void setDireSeriesWins(long direSeriesWins) {
        this.direSeriesWins = direSeriesWins;
    }

    /**
     *
     * @return
     * The seriesType
     */
    public long getSeriesType() {
        return seriesType;
    }

    /**
     *
     * @param seriesType
     * The series_type
     */
    public void setSeriesType(long seriesType) {
        this.seriesType = seriesType;
    }

    /**
     *
     * @return
     * The leagueTier
     */
    public long getLeagueTier() {
        return leagueTier;
    }

    /**
     *
     * @param leagueTier
     * The league_tier
     */
    public void setLeagueTier(long leagueTier) {
        this.leagueTier = leagueTier;
    }

    /**
     *
     * @return
     * The scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     *
     * @param scoreboard
     * The scoreboard
     */
    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
