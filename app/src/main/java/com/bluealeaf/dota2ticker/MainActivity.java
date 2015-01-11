package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

    //Holds the id of the last match in com.bluealeaf.dota2ticker.database


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
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);


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

        matches.clear();
    }



    @Override
    protected void onPause() {
        super.onPause();
        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
    }

    @Subscribe
    public void OnListReceivedFromDb(PassMatchListFromDBEvent event){
        Log.d(tag,String.valueOf(event.getMatchList().size()));
        if(matches.size() != 0){
            matches.clear();
        }
        matches.addAll(event.getMatchList());
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void OnListReceived(UpdateMatchesEvent event){

        Log.d(tag,"OnListReceived");
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(event.getMatches().size() != 0){
            matches.addAll(event.getMatches());
            adapter.notifyDataSetChanged();
        }
    }
}
