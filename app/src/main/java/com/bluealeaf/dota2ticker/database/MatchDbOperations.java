package com.bluealeaf.dota2ticker.database;

import com.bluealeaf.dota2ticker.bus.BusProvider;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import de.greenrobot.dao.query.Query;
import greendao.Match;
import greendao.MatchDao;
/**
 * Created by samidh on 10/1/15.
 */
public class MatchDbOperations {

    public static final String tag = MatchDbOperations.class.getName();

    public static void insertOrUpdate(Match match){
        getMatchDao().insertOrReplace(match);
    }

    public static void delete(Match match){
        getMatchDao().delete(match);
    }

    public static List<Match> getMaxId(){
        Query<Match> query = getMatchDao().queryBuilder()
                                .orderDesc(MatchDao.Properties.Id)
                                .limit(1)
                                .build();
        return query.list();
    }

    public static void insertAll(List<Match> matchList){
        getMatchDao().insertInTx(matchList);
    }

    public static List<Match> getAllMatches(){
        DateTime dt = new DateTime(DateTimeZone.UTC);
        long millis = dt.getMillis();
        List<Match> match = getMatchDao().queryBuilder()
                            .where(MatchDao.Properties.ETA.gt(millis))
                            .orderAsc(MatchDao.Properties.ETA)
                            .build()
                            .list();
        return match;
    }

    public static void updateAlarm(Match match, Boolean flag){
        if(flag){
            match.setAlarm_set(flag);
        }
        else{
            match.setAlarm_set(flag);
        }
        getMatchDao().update(match);
    }

    public static Match getMatch(long id){
        return getMatchDao().queryBuilder().where(MatchDao.Properties.Id.eq(id)).build().unique();
    }

    public static List<Match> search(String searchQuery){
        String modifiedQuery = "%".concat(searchQuery).concat("%");
        Query<Match> query =  getMatchDao().queryBuilder().where(MatchDao.Properties.T1.like(modifiedQuery)).build();
        List<Match> result = query.list();
        query = getMatchDao().queryBuilder().where(MatchDao.Properties.T2.like(modifiedQuery)).build();
        result.addAll(result.size(),query.list());
        return result;
    }



    private static MatchDao getMatchDao(){
        return BusProvider.getDaoSessionInstance().getMatchDao();
    }
}
