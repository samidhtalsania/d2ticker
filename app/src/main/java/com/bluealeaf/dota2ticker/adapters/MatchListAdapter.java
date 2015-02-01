package com.bluealeaf.dota2ticker.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.database.MatchDbOperations;
import com.bluealeaf.dota2ticker.notification.NotificationActivity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import greendao.Match;

/**
 * Created by samidh on 5/1/15.
 */
public class MatchListAdapter extends BaseAdapter {

    private List<Match> match;
    private Context context;
    private long millisCurrent;
    private boolean[] checkedState = new boolean[100];
    private static final String tag = MatchListAdapter.class.getName();


    static class ViewHolder{
        TextView teamOne;
        TextView vs;
        TextView teamTwo;
        TextView ETA;
        Switch alarmSwitch;
    }

    public MatchListAdapter(Context context, List<Match> match) {
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
    public View getView(int position, View convertView, ViewGroup parent) throws  IllegalArgumentException{
        View view = convertView;
        ViewHolder viewHolder;
        final ListView listView = (ListView) parent;
        final Match match_data = match.get(position);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_main_list,null);
            viewHolder = new ViewHolder();
            viewHolder.teamOne = (TextView) view.findViewById(R.id.teamOne);
            viewHolder.teamTwo = (TextView) view.findViewById(R.id.teamTwo);
            viewHolder.vs = (TextView) view.findViewById(R.id.vs);
            viewHolder.ETA = (TextView) view.findViewById(R.id.ETA);
            viewHolder.alarmSwitch = (Switch) view.findViewById(R.id.alarmSwitch);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }



        long temp = match_data.getETA() - millisCurrent;
        temp /= 1000;
        long hours = temp / 3600;
        long mins = (temp % 3600) / 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(hours)).append("h").append(" ").append(String.valueOf(mins)).append("m");

        viewHolder.teamOne.setText(match_data.getT1());
        viewHolder.teamTwo.setText(match_data.getT2());
        viewHolder.vs.setText("vs");
//        viewHolder.alarmSwitch.setTag(position);

        viewHolder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Switch from off to on
                if(isChecked){
                    //if an alarm is already set for this match (match_data.getAlarm_set() == true )then dont do anything
                    //Required until a better solution to listview scroll problem is found.
                    if(match_data.getAlarm_set() == false) {
                        MatchDbOperations.updateAlarm(match_data, true);
                        setAlarm(match_data);
                        match_data.setAlarm_set(true);
                    }

                }

                //switch from on to off
                if(!isChecked){
                    Log.d(tag, "called!");
                    MatchDbOperations.updateAlarm(match_data, false);
                    removeAlarm(match_data);
                    match_data.setAlarm_set(false);

                }
            }
        });
        viewHolder.alarmSwitch.setChecked(match_data.getAlarm_set());
        viewHolder.ETA.setText(sb.toString());

        return view;
    }

    private void setAlarm(Match match){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra("MATCH_ID", match);
        PendingIntent pendingIntent;
        try {
            pendingIntent = PendingIntent.getBroadcast(context, safeLongToInt(match.getId())+65535, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        catch(IllegalArgumentException ex){
            throw ex;
        }
        //300000 = 5 mins. Alarm gonna ring 5 mins before time
        alarmManager.set(AlarmManager.RTC_WAKEUP,match.getETA()*1000-300000, pendingIntent);
    }

    private void removeAlarm(Match match){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra("MATCH_ID", (Parcelable)match);
        PendingIntent pendingIntent;
        try {
            pendingIntent = PendingIntent.getService(context, safeLongToInt(match.getId())+65535, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        catch(IllegalArgumentException ex){
            throw ex;
        }
        alarmManager.cancel(pendingIntent);
    }

    public int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}