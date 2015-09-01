package com.bluealeaf.dota2ticker.events;

import java.util.ArrayList;

import greendao.Match;

/**
 * Created by samidh on 6/3/15.
 */
public class FilteredMatchesEvent {
    private CharSequence constraint;
    private ArrayList<Match> values;

    public FilteredMatchesEvent(CharSequence constraint, ArrayList<Match> values) {
        this.constraint = constraint;
        this.values = values;
    }

    public CharSequence getConstraint(){
        return constraint;
    }

    public ArrayList<Match> getValues(){
        return values;
    }
}
