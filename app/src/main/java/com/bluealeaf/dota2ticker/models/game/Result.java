package com.bluealeaf.dota2ticker.models.game;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by samidh on 2/5/15.
 */

public class Result {

    @Expose
    private ArrayList<Game> games = new ArrayList<Game>();
    @Expose
    private long status;

    public Result(){
        status = 0 ;
        games.add(new Game());
    }

    /**
     *
     * @return
     * The games
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     *
     * @param games
     * The games
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    /**
     *
     * @return
     * The status
     */
    public long getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(long status) {
        this.status = status;
    }

}
