package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.GetIdFromDbEvent;
import com.bluealeaf.dota2ticker.events.PassMatchListEvent;
import com.bluealeaf.dota2ticker.models.Match;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends ActionBarActivity {

    //Holds the id of the last match in database
    private int id;
    private List<Match> matches;
    private ListView listView;
    private MatchListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;



    private static final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matches = new ArrayList<Match>();
        listView = (ListView) findViewById(R.id.match_list_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                BusProvider.getInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));
            }
        });

        Log.d(tag, "Create");
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Register subscribed event
        BusProvider.getInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_CACHE));
        Log.d(tag, "Resume");
    }



    @Override
    protected void onPause() {
        super.onPause();
        //Unregister subscribed event
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void OnListReceived(PassMatchListEvent event){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        if(event != null) {
            matches = event.getMatchList();
            adapter = new MatchListAdapter(this, matches);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, Errors.Retrofit_error,Toast.LENGTH_LONG);
        }
    }

}
