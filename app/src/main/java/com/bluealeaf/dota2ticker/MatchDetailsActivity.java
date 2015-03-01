package com.bluealeaf.dota2ticker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import greendao.Match;



public class MatchDetailsActivity extends ActionBarActivity {

    private Match match ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO:Add ads
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        final Match match = getIntent().getParcelableExtra("MATCH_ID");

        if(match != null){

            TextView teamOne = (TextView) findViewById(R.id.t1Bold);
            TextView teamTwo = (TextView) findViewById(R.id.t2Bold);

            ImageView teamOneImg = (ImageView) findViewById(R.id.t1Logo);
            ImageView teamTwoImg = (ImageView) findViewById(R.id.t2Logo);

            teamOne.setText(match.getT1());
            teamTwo.setText(match.getT2());

            int t1Resource = this.getResources().getIdentifier(this.getPackageName()+":drawable/"+match.getT1().replace(" ","_").toLowerCase()+"_60px",null,null);
            int t2Resource = this.getResources().getIdentifier(this.getPackageName()+":drawable/"+match.getT2().replace(" ","_").toLowerCase()+"_60px",null,null);

            if(t1Resource == 0){
                t1Resource = this.getResources().getIdentifier(this.getPackageName()+":drawable/unknown_30px",null,null);
            }

            if(t2Resource == 0){
                t2Resource = this.getResources().getIdentifier(this.getPackageName()+":drawable/unknown_30px",null,null);
            }

            teamOneImg.setImageResource(t1Resource);
            teamTwoImg.setImageResource(t2Resource);

            final TextView status = (TextView) findViewById(R.id.matchStatus);


            long time = match.getETA() - DateTime.now(DateTimeZone.UTC).getMillis();
            time /= 1000;
            long hours = time / 3600;
            long mins = (time % 3600) / 60;

            StringBuilder sb = new StringBuilder();
            sb.append("in ").append(String.valueOf(hours)).append("h").append(" ").append(String.valueOf(mins)).append("m");

            if(DateTime.now(DateTimeZone.UTC).getMillis() > match.getETA()-30000){
                status.setText("LIVE");
            }
            else{
                status.setText(sb.toString());
                status.setTextColor(getResources().getColor(R.color.black));
            }

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                this.setTitle(match.getT1() + " VS " + match.getT2());
            }
            else{
                String t1 = match.getT1(),t2 = match.getT2();

                String[] team = t1.split(" ");
                if(team.length > 1){
                    t1 = "" ;
                    for(String split : team){
                        char c = split.toUpperCase().charAt(0);
                        t1 += c ;
                    }
                }

                team = t2.split(" ");
                if(team.length > 1){
                    t2 = "" ;
                    for(String split : team){
                        char c = split.toUpperCase().charAt(0);
                        t2 += c ;
                    }
                }
                this.setTitle(t1 + " vs " + t2);

            }



            ImageButton shareButton = (ImageButton) findViewById(R.id.share);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    StringBuilder sb = new StringBuilder();
                    sendIntent.putExtra(Intent.EXTRA_TEXT, sb.append(match.getT1()).append(" vs ").append(match.getT2()).append(" ").append(status.getText()).toString());
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share using..."));
                }
            });

        }
        else {
            TextView status = (TextView) findViewById(R.id.matchStatus);
            status.setText("No match");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_match_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
