package com.bluealeaf.dota2ticker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.bluealeaf.dota2ticker.util.MenuActions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

    private ArrayList<greendao.Match> matches ;

    private boolean isSwiped = false;

    private Context mContext;

    private static final String tag = MainActivity.class.getName();
    private static final String INDEX = "INDEX";
    private static final String TOP = "TOP";

    private AdView mAdView;

    private int top;
    private int index;
    private boolean isInstanceSaved;

    private static final String DEBUG_TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Make app snappier. Make db calls in background worker thread.
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });

        mContext = this;

        if(savedInstanceState != null){
            isInstanceSaved = true;
            index = savedInstanceState.getInt(INDEX);

            top = savedInstanceState.getInt(TOP);
        }
        else{
            isInstanceSaved = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView = (ListView) findViewById(R.id.match_list_view);
        matches = new ArrayList<greendao.Match>();
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isSwiped = true;
                matches.clear();
                BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match match = matches.get(position);
                Intent intent = new Intent(mContext,MatchDetailsActivity.class);
                intent.putExtra("MATCH_ID", match);

                startActivity(intent);

            }
        });

        mAdView.resume();

        //Register subscribed event
        BusProvider.getBusInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_CACHE));
    }


    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pause();

        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        mAdView.destroy();

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
//        bundle.putParcelableArrayList("Matches",matches);
        int index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
        bundle.putInt(INDEX,index);
        bundle.putInt(TOP,top);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                MenuActions.openSettings(this);
//                Intent intent = new Intent(this,SettingsActivity.class);
//                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Subscribe
    public void OnListReceivedFromDb(PassMatchListFromDBEvent event){
        updateMatches(event.getMatchList());
        adapter.notifyDataSetChanged();
        if(isInstanceSaved && !isSwiped){
            listView.setSelectionFromTop(index, top);
            Log.d(tag,"called");
        }
    }

    @Subscribe
    public void OnListReceived(UpdateMatchesEvent event){

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if(isSwiped){
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
    }

    @Subscribe
    public void OnConnectionError(ConnectionErrorEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
        Toast.makeText(this,event.getMessage(),Toast.LENGTH_LONG).show();
        //TODO
        //why is this used when No extra match data is coming. maybe because if not written it will show an empty list.
        // Need to check this
        adapter.notifyDataSetChanged();

    }

    @Subscribe
    public void OnNoNewMatchesReceived(NoNewMatchesEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
        //TODO
        //why is this used when No extra match data is coming. maybe because if not written it will show an empty list.
        // Need to check this
        adapter.notifyDataSetChanged();
    }

    private synchronized void  updateMatches(List<Match> matches){
        this.matches.addAll(matches);
    }


}
