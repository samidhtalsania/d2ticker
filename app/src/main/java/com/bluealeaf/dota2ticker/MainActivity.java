package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.GetIdFromDbEvent;
import com.bluealeaf.dota2ticker.events.PassMatchListFromDBEvent;
import com.bluealeaf.dota2ticker.events.UpdateMatchesEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends ActionBarActivity {

    //Holds the id of the last match in database


    private ListView listView;
    private MatchListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    List<greendao.Match> matches ;

    private static final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matches = new ArrayList<greendao.Match>();
        listView = (ListView) findViewById(R.id.match_list_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Register subscribed event
        BusProvider.getBusInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_CACHE));
    }



    @Override
    protected void onPause() {
        super.onPause();
        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
    }

    @Subscribe
    public void OnListReceivedFromDb(PassMatchListFromDBEvent event){
        matches = event.getMatchList();
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);
    }

    @Subscribe
    public void OnListReceived(UpdateMatchesEvent event){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(event != null){
            matches.addAll(event.getMatches());
            adapter.notifyDataSetChanged();
        }
    }



}
