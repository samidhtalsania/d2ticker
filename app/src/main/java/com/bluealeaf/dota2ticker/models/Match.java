package com.bluealeaf.dota2ticker.models;

/**
 * Created by samidh on 4/1/15.
 */



import com.google.gson.annotations.Expose;

public class Match {

    @Expose
    private String t1c;
    @Expose
    private String t2c;
    @Expose
    private String t2;
    @Expose
    private String t1;
    @Expose
    private String ETA;
    @Expose
    private long id;

    /**
     *
     * @return
     * The t1c
     */
    public String getT1c() {
        return t1c;
    }

    /**
     *
     * @param t1c
     * The t1c
     */
    public void setT1c(String t1c) {
        this.t1c = t1c;
    }

    /**
     *
     * @return
     * The t2c
     */
    public String getT2c() {
        return t2c;
    }

    /**
     *
     * @param t2c
     * The t2c
     */
    public void setT2c(String t2c) {
        this.t2c = t2c;
    }

    /**
     *
     * @return
     * The t2
     */
    public String getT2() {
        return t2;
    }

    /**
     *
     * @param t2
     * The t2
     */
    public void setT2(String t2) {
        this.t2 = t2;
    }

    /**
     *
     * @return
     * The t1
     */
    public String getT1() {
        return t1;
    }

    /**
     *
     * @param t1
     * The t1
     */
    public void setT1(String t1) {
        this.t1 = t1;
    }

    /**
     *
     * @return
     * The ETA
     */
    public String getETA() {
        return ETA;
    }

    /**
     *
     * @param ETA
     * The ETA
     */
    public void setETA(String ETA) {
        this.ETA = ETA;
    }

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

}
