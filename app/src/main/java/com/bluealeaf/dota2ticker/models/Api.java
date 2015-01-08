package com.bluealeaf.dota2ticker.models;

/**
 * Created by samidh on 4/1/15.
 */


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;


public class Api {

    @Expose
    private List<Match> matches = new ArrayList<Match>();
    @Expose
    private int total;
    @Expose
    private boolean success;

    /**
     *
     * @return
     * The Matches
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     *
     * @param matches
     * The Matches
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    /**
     *
     * @return
     * The total
     */
    public int getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}


