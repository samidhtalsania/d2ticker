package com.bluealeaf.dota2ticker.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.adapters.MatchLiveListAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.events.GetLiveMatchesEvent;
import com.bluealeaf.dota2ticker.events.PassLiveMatchListFromNetEvent;
import com.bluealeaf.dota2ticker.models.Game;
import com.squareup.otto.Subscribe;

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


        return layout;


    }

    @Override
    public void onResume() {
        super.onResume();

        listView = (ListView) mActivity.findViewById(R.id.match_live_list_view);
        games = new ArrayList<Game>();
        adapter = new MatchLiveListAdapter(mContext,games);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = games.get(position);
            }
        });


        BusProvider.getBusInstance().register(this);
        BusProvider.getBusInstance().post(new GetLiveMatchesEvent());
    }

    @Override
    public void onPause(){
        super.onPause();
        //Unregister subscribed event
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void onMatchReceived(PassLiveMatchListFromNetEvent event){
        games = event.getMatchList();
        adapter.setGames(games);
        adapter.notifyDataSetChanged();
    }

}
