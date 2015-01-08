package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.events.GetIdEvent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matches = new ArrayList<Match>();
        listView = (ListView) findViewById(R.id.match_list_view);
        Log.d("MainActivity-onCreate", "Here");
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Register subscribed event
        BusProvider.getInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getInstance().post(new GetIdEvent());
        Log.d("MainActivity-onResume", "Here");
    }



    @Override
    protected void onPause() {
        super.onPause();
        //Unregister subscribed event
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void OnListReceived(PassMatchListEvent event){

        matches = event.getMatchList();
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);
    }
}
