package com.bluealeaf.dota2ticker.events;

import java.util.ArrayList;
import java.util.List;

import greendao.Match;

/**
 * Created by samidh on 10/1/15.
 */
public class UpdateMatchesEvent {
    private static final String tag = UpdateMatchesEvent.class.getName();
    private List<Match> matches ;
    public UpdateMatchesEvent(List<Match> matches){
        this.matches = new ArrayList<Match>();
        this.matches = matches;
    }


    public List<Match> getMatches() {
        return matches;
    }
}
