package com.bluealeaf.dota2ticker.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluealeaf.dota2ticker.GameDetailsActivityTabbed;
import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.adapters.MatchLiveListAdapter;
import com.bluealeaf.dota2ticker.adapters.MatchLiveListAdapter.ViewHolder;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.events.GetLiveMatchesEvent;
import com.bluealeaf.dota2ticker.events.PassLiveMatchListFromNetEvent;
import com.bluealeaf.dota2ticker.events.TeamLogoReceivedEvent;
import com.bluealeaf.dota2ticker.models.Team_img;
import com.bluealeaf.dota2ticker.models.game.Game;
import com.bluealeaf.dota2ticker.util.Internet;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LiveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveFragment extends android.support.v4.app.Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";


    private int mParam1;
    private Activity mActivity;
    private Context mContext;
    private ListView listView;
    private MatchLiveListAdapter adapter;
    private ArrayList<Game> games;

    private boolean isSwiped = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout emptySwipeRefreshLayout;

    private static final String INDEX = "INDEX";
    private static final String TOP = "TOP";

    private int top;
    private int index;
    private boolean isInstanceSaved;

    private OnFragmentInteractionListener mListener;


    public static LiveFragment newInstance(int position) {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    public LiveFragment() {
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = mActivity = this.getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_live,container,false);

        if(savedInstanceState != null){
            isInstanceSaved = true;
            index = savedInstanceState.getInt(INDEX);

            top = savedInstanceState.getInt(TOP);
        }
        else{
            isInstanceSaved = false;
        }

        return layout;


    }

    @Override
    public void onResume() {
        super.onResume();

        listView = (ListView) mActivity.findViewById(R.id.match_live_list_view);
        games = new ArrayList<Game>();
        adapter = new MatchLiveListAdapter(mContext,games);

        swipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipe_live);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(Internet.isNetworkAvailable(mActivity)){
                    isSwiped = true;
                    games.clear();
                    BusProvider.getBusInstance().post(new GetLiveMatchesEvent());
                }
                else{
                    Toast.makeText(mContext, mActivity.getResources().getText(R.string.msg_no_internet), Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        emptySwipeRefreshLayout = (SwipeRefreshLayout) mActivity.findViewById(R.id.swipeRefreshLayout_emptyView_live);
        emptySwipeRefreshLayout.setColorSchemeResources(
                R.color.blue, R.color.purple,
                R.color.green, R.color.orange);
        emptySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(Internet.isNetworkAvailable(mActivity)){
                    isSwiped = true;
                    games.clear();
                    BusProvider.getBusInstance().post(new GetLiveMatchesEvent());
                }
                else{
                    Toast.makeText(mContext,mActivity.getResources().getText(R.string.msg_no_internet),Toast.LENGTH_LONG).show();
                    emptySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = games.get(position);
                BusProvider.game = game;
                Intent intent = new Intent(mContext,GameDetailsActivityTabbed.class);
                startActivity(intent);

            }
        });


        listView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);

        BusProvider.getBusInstance().register(this);
        BusProvider.getBusInstance().post(new GetLiveMatchesEvent());
    }

    @Override
    public void onPause(){
        super.onPause();
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
    }


    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        int index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
        bundle.putInt(INDEX,index);
        bundle.putInt(TOP,top);
    }



    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Subscribe
    public void onMatchReceived(PassLiveMatchListFromNetEvent event){
        games = event.getMatchList();
        adapter.setGames(games);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        listView.setEmptyView(emptySwipeRefreshLayout);
        ImageView iv  = (ImageView) mActivity.findViewById(R.id.no_connection_icon_live);
        TextView tv= (TextView) mActivity.findViewById(R.id.emptyView_live);
        if(games.size() == 0){
            //No matches to show
            iv.setVisibility(View.GONE);
            tv.setText(mActivity.getResources().getText(R.string.msg_no_matches_live));
        }
        else{
            iv.setVisibility(View.VISIBLE);
            tv.setText(mActivity.getResources().getText(R.string.msg_no_matches_error_live));
        }
        RelativeLayout rLayout = (RelativeLayout) mActivity.findViewById(R.id.empty_container_live);
        rLayout.setVisibility(View.VISIBLE);

    }

    @Subscribe
    public void getTeamLogo(TeamLogoReceivedEvent event){

        if(listView.getChildCount() > 0) {
            View view = getViewByPosition(event.getPosition(), listView);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            Team_img image = event.getImage();
            if (event.getTeam().equalsIgnoreCase("R")) {
                if(image.getSuccess().equals("True")){
                    Picasso.with(mContext).load(event.getImageUrl()).into(viewHolder.teamOneCnt);
                }
            }

            if (event.getTeam().equalsIgnoreCase("D")) {
                if(image.getSuccess().equals("True")) {
                    Picasso.with(mContext).load(event.getImageUrl()).into(viewHolder.teamTwoCnt);
                }
            }
        }
    }


    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }



}
