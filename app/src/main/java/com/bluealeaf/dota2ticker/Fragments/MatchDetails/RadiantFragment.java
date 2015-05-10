package com.bluealeaf.dota2ticker.Fragments.MatchDetails;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.adapters.RadiantPlayerDetailsAdapter;
import com.bluealeaf.dota2ticker.bus.BusProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadiantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadiantFragment extends android.support.v4.app.Fragment {

    private static final String POSITION = "position";
    private int mParam1;
    private Context mContext;
    private Activity mActivity;

    private ListView listView;
    private RadiantPlayerDetailsAdapter radiantAdapter;

    public static RadiantFragment newInstance(int position) {
        RadiantFragment fragment = new RadiantFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public RadiantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(POSITION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radiant, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = mActivity = this.getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
        listView = (ListView) getView().findViewById(R.id.radiant_heroes);
        radiantAdapter = new RadiantPlayerDetailsAdapter(mContext, BusProvider.game.getScoreboard().getRadiant().getPlayers());
        listView.setAdapter(radiantAdapter);
        radiantAdapter.notifyDataSetChanged();
    }



}
