package com.bluealeaf.dota2ticker.models.game;

import com.google.gson.annotations.Expose;

/**
 * Created by samidh on 2/5/15.
 */
public class LiveMatch {

    @Expose
    private Result result;

    public LiveMatch(){
        result = new Result();
    }

    /**
     *
     * @return
     * The result
     */
    public Result getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

}
