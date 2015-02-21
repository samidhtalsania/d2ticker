package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import greendao.Match;


public class MatchDetailsActivity extends ActionBarActivity {

    private Match match ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        Match match = getIntent().getParcelableExtra("MATCH_ID");
        getActionBar().setHomeButtonEnabled(true);

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

            TextView status = (TextView) findViewById(R.id.matchStatus);


            long temp = match.getETA() - DateTime.now(DateTimeZone.UTC).getMillis();
            temp /= 1000;
            long hours = temp / 3600;
            long mins = (temp % 3600) / 60;

            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(hours)).append("h").append(" ").append(String.valueOf(mins)).append("m");

            if(DateTime.now(DateTimeZone.UTC).getMillis() > match.getETA()-30000){
                status.setText("LIVE");
            }
            else{
                status.setText(sb.toString());
                status.setTextColor(getResources().getColor(R.color.black));
            }

            this.setTitle(match.getT1() + " VS " + match.getT2());

        }
        else {
            TextView status = (TextView) findViewById(R.id.matchStatus);
            status.setText("No match");
        }
    }


}
