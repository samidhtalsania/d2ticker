package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.ConnectionErrorEvent;
import com.bluealeaf.dota2ticker.events.GetIdFromDbEvent;
import com.bluealeaf.dota2ticker.events.NoNewMatchesEvent;
import com.bluealeaf.dota2ticker.events.PassMatchListFromDBEvent;
import com.bluealeaf.dota2ticker.events.UpdateMatchesEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import greendao.Match;


public class MainActivity extends ActionBarActivity {




    private ListView listView;
    private MatchListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<greendao.Match> matches ;

    private boolean isSwiped = false;

    private static final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    protected void onResume() {
        super.onResume();

        listView = (ListView) findViewById(R.id.match_list_view);
        matches = new ArrayList<greendao.Match>();
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);
        Log.d(tag,String.valueOf(matches.size()));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));
                isSwiped = true;
            }
        });

        //Register subscribed event
        BusProvider.getBusInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_CACHE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag,"pause");
        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
    }

    @Subscribe
    public void OnListReceivedFromDb(PassMatchListFromDBEvent event){
        updateMatches(event.getMatchList());
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void OnListReceived(UpdateMatchesEvent event){

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if(isSwiped){
            Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();
            isSwiped = false;
        }

        if (event.getMatches().size() != 0) {
            updateMatches(event.getMatches());
            Collections.sort(matches, new Comparator<Match>() {
                @Override
                public int compare(Match lhs, Match rhs) {
                    return lhs.getETA().compareTo(rhs.getETA());
                }
            });
            adapter.notifyDataSetChanged();
        }

        Log.d(tag,"OnListReceived");

    }

    @Subscribe
    public void OnConnectionError(ConnectionErrorEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
        Toast.makeText(this,event.getMessage(),Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
        Log.d(tag,"OnConnectionError");
    }

    @Subscribe
    public void OnNoNewMatchesReceived(NoNewMatchesEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
        Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }

    private synchronized void  updateMatches(List<Match> matches){
        this.matches.addAll(matches);
    }
}
