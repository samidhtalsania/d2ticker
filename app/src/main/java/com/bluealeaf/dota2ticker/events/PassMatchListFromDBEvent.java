package com.bluealeaf.dota2ticker.events;

import java.util.ArrayList;
import java.util.List;

import greendao.Match;

/**
 * Created by samidh on 10/1/15.
 */
public class PassMatchListFromDBEvent {

    List<Match> matchList = new ArrayList<Match>();

    public PassMatchListFromDBEvent(List<Match> matchList){
        this.matchList = matchList;
    }

    public List<Match> getMatchList(){
        return matchList;
    }
}
