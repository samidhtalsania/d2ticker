package com.bluealeaf.dota2ticker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.models.Game;

import java.util.ArrayList;

/**
 * Created by samidh on 2/5/15.
 */
public class MatchLiveListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Game> games;

    static class ViewHolder{
        TextView teamOne;
        TextView vs;
        TextView teamTwo;
        ImageView teamOneCnt;
        ImageView teamTwoCnt;
    }

    public MatchLiveListAdapter(Context context, ArrayList<Game> games){
        this.context = context;
        this.games = games;
    }

    public void setGames(ArrayList<Game> games){
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final Game game = games.get(position);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_live_list,null);
            viewHolder = new ViewHolder();
            viewHolder.teamOne = (TextView) view.findViewById(R.id.teamOneLive);
            viewHolder.teamTwo = (TextView) view.findViewById(R.id.teamTwoLive);
            viewHolder.vs = (TextView) view.findViewById(R.id.vsLive);
            viewHolder.teamOneCnt = (ImageView) view.findViewById(R.id.teamOneCntLive);
            viewHolder.teamTwoCnt = (ImageView) view.findViewById(R.id.teamTwoCntLive);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.teamOne.setText(game.getRadiantTeam().getTeamName());
        viewHolder.teamTwo.setText(game.getDireTeam().getTeamName());
        viewHolder.vs.setText("vs");


        viewHolder.teamOneCnt.setImageResource(R.drawable.ic_question);
        viewHolder.teamTwoCnt.setImageResource(R.drawable.ic_question);

        return view;
    }



}
