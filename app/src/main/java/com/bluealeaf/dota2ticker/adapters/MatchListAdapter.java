package com.bluealeaf.dota2ticker.adapters;

import android.app.Activity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluealeaf.dota2ticker.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import greendao.Match;

//import android.widget.Switch;

/**
 * Created by samidh on 5/1/15.
 */
public class MatchListAdapter extends BaseAdapter {

    private List<Match> match;
    private Activity context;
    private long millisCurrent;

    static class ViewHolder{
        public TextView teamOne;
        public TextView vs;
        public TextView teamTwo;
        public TextView ETA;
        public SwitchCompat alarmSwitch;
    }

    public MatchListAdapter(Activity context, List<Match> match) {
        this.match = match;
        this.context = context;
        millisCurrent = DateTime.now(DateTimeZone.UTC).getMillis();
    }


    @Override
    public int getCount() {
        return match.size();
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

        if(view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_main_list,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.teamOne = (TextView) view.findViewById(R.id.teamOne);
            viewHolder.teamTwo = (TextView) view.findViewById(R.id.teamTwo);
            viewHolder.vs = (TextView) view.findViewById(R.id.vs);
            viewHolder.ETA = (TextView) view.findViewById(R.id.ETA);
            viewHolder.alarmSwitch = (SwitchCompat) view.findViewById(R.id.alarmSwitch);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        Match match_data = match.get(position);
        viewHolder.teamOne.setText(match_data.getT1());
        viewHolder.teamTwo.setText(match_data.getT2());
        viewHolder.vs.setText("vs");

        long temp = match_data.getETA() - millisCurrent;
        temp /= 1000;
        long hours = temp / 3600;
        long mins = (temp%3600) / 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(hours)).append("h").append(" ").append(String.valueOf(mins)).append("m");
        viewHolder.ETA.setText(sb.toString());

        return view;
    }
}