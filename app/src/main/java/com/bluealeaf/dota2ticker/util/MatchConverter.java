package com.bluealeaf.dota2ticker.util;

import com.bluealeaf.dota2ticker.models.Match;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by samidh on 10/1/15.
 */

/*

Utility class that converts models.Match data object to greendao,Match data object

 */

public class MatchConverter {

    public static List<greendao.Match> netToDB(List<Match> matches){
        List<greendao.Match> matchList = new ArrayList<greendao.Match>();
        greendao.Match newMatch;

        for(Match match : matches){
            newMatch = new greendao.Match();
            newMatch.setId(match.getId());
            newMatch.setT1(match.getT1());
            newMatch.setT1c(match.getT1c());
            newMatch.setT2(match.getT2());
            newMatch.setT2c(match.getT2c());
            newMatch.setAlarm_set(false);
            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime dt = df.withZone(DateTimeZone.UTC).parseDateTime(match.getETA());
            long seconds = dt.getMillis();

            newMatch.setETA(seconds);
            matchList.add(newMatch);
        }
        return matchList;
    }

    public static List<Match> greenDaoToDb(List<greendao.Match> matches){
        List<Match> matchList = new ArrayList<Match>();
        Match newMatch;
        for(greendao.Match match : matches){
            newMatch = new Match();
            newMatch.setId(match.getId());
            newMatch.setT1(match.getT1());
            newMatch.setT1c(match.getT1c());
            newMatch.setT2(match.getT2());
            newMatch.setT2c(match.getT2c());


        }

        return matchList;
    }

}
