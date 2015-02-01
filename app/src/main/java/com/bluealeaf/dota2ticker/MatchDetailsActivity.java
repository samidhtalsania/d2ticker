package com.bluealeaf.dota2ticker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import greendao.Match;


public class MatchDetailsActivity extends ActionBarActivity {

    private Match match ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        Match match = getIntent().getParcelableExtra("MATCH_ID");
        TextView textView = (TextView) findViewById(R.id.data);
        if(match != null){


            textView.setText(match.getT1()+" vs. "+match.getT2());
        }
        else {
            textView.setText("No match found.");
        }
    }


}
