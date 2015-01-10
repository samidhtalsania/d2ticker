package database;

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
        List<Match> match = getMatchDao().queryBuilder().where(MatchDao.Properties.ETA.gt(millis)).build().list();
        return match;
    }

    private static MatchDao getMatchDao(){
        return BusProvider.getDaoSessionInstance().getMatchDao();
    }
}
