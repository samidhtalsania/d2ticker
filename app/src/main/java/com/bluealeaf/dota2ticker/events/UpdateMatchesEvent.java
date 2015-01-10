package com.bluealeaf.dota2ticker.events;

import java.util.ArrayList;
import java.util.List;

import greendao.Match;

/**
 * Created by samidh on 10/1/15.
 */
public class UpdateMatchesEvent {

    private List<Match> matches ;
    public UpdateMatchesEvent(List<Match> matches){
        matches = new ArrayList<Match>();
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
