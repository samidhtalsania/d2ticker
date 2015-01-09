package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.events.GetIdEvent;
import com.bluealeaf.dota2ticker.events.PassMatchListEvent;
import com.bluealeaf.dota2ticker.models.Match;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;


public class MainActivity extends ActionBarActivity {

    //Holds the id of the last match in database
    private int id;
    private List<Match> matches;
    private ListView listView;
    private MatchListAdapter adapter;
    private PtrFrameLayout frame;

    private static final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matches = new ArrayList<Match>();
        listView = (ListView) findViewById(R.id.match_list_view);


        frame = (PtrFrameLayout) findViewById(R.id.material_style_ptr_frame);

        // header
        final MaterialHeader header = new MaterialHeader(this.getApplicationContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPtrFrameLayout(frame);

        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                //calls OnRequestForId method in GetMatchesEvent.java
                BusProvider.getInstance().post(new GetIdEvent());
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
        BusProvider.getInstance().post(new GetIdEvent());
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
        frame.refreshComplete();
        matches = event.getMatchList();
        adapter = new MatchListAdapter(this,matches);
        listView.setAdapter(adapter);
    }
}
