package com.bluealeaf.dota2ticker.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluealeaf.dota2ticker.MatchDetailsActivity;
import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.adapters.MatchListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.constants.Errors;
import com.bluealeaf.dota2ticker.constants.OkHttpClientConst;
import com.bluealeaf.dota2ticker.events.ConnectionErrorEvent;
import com.bluealeaf.dota2ticker.events.FilteredMatchesEvent;
import com.bluealeaf.dota2ticker.events.GetIdFromDbEvent;
import com.bluealeaf.dota2ticker.events.NoNewMatchesEvent;
import com.bluealeaf.dota2ticker.events.PassMatchListFromDBEvent;
import com.bluealeaf.dota2ticker.events.RestErrorEvent;
import com.bluealeaf.dota2ticker.events.UpdateMatchesEvent;
import com.bluealeaf.dota2ticker.util.Internet;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import greendao.Match;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends android.support.v4.app.Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";

    private ListView listView;
    private MatchListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout emptySwipeRefreshLayout;

    private ArrayList<Match> matches ;

    private boolean isSwiped = false;

    private Context mContext;
    private Activity mActivity;

    private static final String tag = UpcomingFragment.class.getName();
    private static final String INDEX = "INDEX";
    private static final String TOP = "TOP";

    private AdView mAdView;

    private int top;
    private int index;
    private boolean isInstanceSaved;

    private static final String DEBUG_TAG = "CalendarActivity";


    private int mParam1;


    private OnFragmentInteractionListener mListener;


    public static UpcomingFragment newInstance(int position) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_POSITION);

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(mActivity.getComponentName()));
        searchView.setIconifiedByDefault(false);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchView.setQuery("", false);
                Log.d("search", "expand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.restoreOriginalList();
                adapter.notifyDataSetChanged();
                return true;
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = mActivity = this.getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LinearLayout llayout = (LinearLayout) inflater.inflate(R.layout.fragment_upcoming,container,false);

        mAdView = (AdView) llayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });



        if(savedInstanceState != null){
            isInstanceSaved = true;
            index = savedInstanceState.getInt(INDEX);

            top = savedInstanceState.getInt(TOP);
        }
        else{
            isInstanceSaved = false;
        }
        return llayout;
    }

    @Override
    public void onResume(){
        super.onResume();

        listView = (ListView) mActivity.findViewById(R.id.match_list_view);
        matches = new ArrayList<greendao.Match>();
        adapter = new MatchListAdapter(mActivity,matches);



        swipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(Internet.isNetworkAvailable(mActivity)){
                    isSwiped = true;
                    matches.clear();
                    BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));
                }
                else{
                    Toast.makeText(mContext, "Please connect to internet.", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        emptySwipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipeRefreshLayout_emptyView);
        emptySwipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        emptySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(Internet.isNetworkAvailable(mActivity)){
                    isSwiped = true;
                    matches.clear();
                    BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_NETWORK));
                }
                else{
                    Toast.makeText(mContext,"Please connect to internet.",Toast.LENGTH_LONG).show();
                    emptySwipeRefreshLayout.setRefreshing(false);
                }
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

        listView.setEmptyView(emptySwipeRefreshLayout);
        listView.setAdapter(adapter);

        mAdView.resume();

        //Register subscribed event
        BusProvider.getBusInstance().register(this);
        //Post an event to get List of Matches
        BusProvider.getBusInstance().post(new GetIdFromDbEvent(OkHttpClientConst.FORCE_CACHE));
    }


    @Override
    public void onPause(){
        super.onPause();
        mAdView.pause();

        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
        index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
        isInstanceSaved = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAdView.destroy();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
            isSwiped = false;
        }

        if(emptySwipeRefreshLayout.isRefreshing()){
            emptySwipeRefreshLayout.setRefreshing(false);
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
        //There was no error. THere are no matches to show.
        else{
            TextView tv = (TextView) mActivity.findViewById(R.id.emptyView);
            tv.setText("There are no matches Scheduled. Go enjoy a game of Dota2 and check back later.");
            ImageView iv = (ImageView) mActivity.findViewById(R.id.no_connection_icon);
            iv.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void OnConnectionError(ConnectionErrorEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }

        if(emptySwipeRefreshLayout.isRefreshing()){
            emptySwipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
//        Toast.makeText(this,event.getMessage(),Toast.LENGTH_LONG).show();
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

        if(emptySwipeRefreshLayout.isRefreshing()){
            emptySwipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }
        //TODO
        //why is this used when No extra match data is coming. maybe because if not written it will show an empty list.
        // Need to check this
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void OnError(RestErrorEvent event){
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }

        if(emptySwipeRefreshLayout.isRefreshing()){
            emptySwipeRefreshLayout.setRefreshing(false);
            isSwiped = false;
        }

        if(event.getMessage().equals(Errors.Retrofit_NETWORK)){
            //Network error + no matches
            if(matches.size() == 0){
                TextView tv = (TextView) mActivity.findViewById(R.id.emptyView);
                tv.setText("Not connected to internet. Swipe down after connecting to internet.");
                ImageView iv = (ImageView) mActivity.findViewById(R.id.no_connection_icon);
                iv.setVisibility(View.VISIBLE);
            }
        }


    }

    @Subscribe
    public void OnFilteredMatchesReceived(FilteredMatchesEvent event){
        if(event.getConstraint().toString().length() > 0 ){
            adapter.setFilteredList(event.getValues());
            adapter.notifyDataSetChanged();
            this.matches = event.getValues();
            if(event.getValues().size() == 0){
                TextView tv = (TextView) mActivity.findViewById(R.id.emptyView);
                tv.setText("No matches for this team.");
                ImageView iv = (ImageView) mActivity.findViewById(R.id.no_connection_icon);
                iv.setVisibility(View.GONE);
            }
        }
        else{
            this.matches = adapter.restoreOriginalList();
            adapter.notifyDataSetChanged();
        }
    }


    private synchronized void  updateMatches(List<Match> matches){
        this.matches.addAll(matches);
    }

}
