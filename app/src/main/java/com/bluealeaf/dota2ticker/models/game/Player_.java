package com.bluealeaf.dota2ticker.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samidh on 2/5/15.
 */
public class Player_ {

    @SerializedName("player_slot")
    @Expose
    private long playerSlot;
    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("hero_id")
    @Expose
    private long heroId;
    @Expose
    private long kills;
    @Expose
    private long death;
    @Expose
    private long assists;
    @SerializedName("last_hits")
    @Expose
    private long lastHits;
    @Expose
    private long denies;
    @Expose
    private long gold;
    @Expose
    private long level;
    @SerializedName("gold_per_min")
    @Expose
    private long goldPerMin;
    @SerializedName("xp_per_min")
    @Expose
    private long xpPerMin;
    @SerializedName("ultimate_state")
    @Expose
    private long ultimateState;
    @SerializedName("ultimate_cooldown")
    @Expose
    private long ultimateCooldown;
    @Expose
    private long item0;
    @Expose
    private long item1;
    @Expose
    private long item2;
    @Expose
    private long item3;
    @Expose
    private long item4;
    @Expose
    private long item5;
    @SerializedName("respawn_timer")
    @Expose
    private long respawnTimer;
    @SerializedName("position_x")
    @Expose
    private double positionX;
    @SerializedName("position_y")
    @Expose
    private double positionY;
    @SerializedName("net_worth")
    @Expose
    private long netWorth;


    public Player_(){
        netWorth = 0 ;
        positionY = 0 ;
        positionX = 0 ;
        respawnTimer = 0 ;
        item0 = 0 ;
        item1 = 0 ;
        item2 = 0 ;
        item3 = 0 ;
        item4 = 0 ;
        item5 = 0 ;
        ultimateCooldown = 0 ;
        ultimateState = 0 ;
        xpPerMin = 0;
        goldPerMin = 0 ;
        level = 0 ;
        gold = 0 ;
        denies = 0 ;
        lastHits = 0 ;
        assists =  0;
        death = 0 ;
        kills = 0 ;
        heroId = 0 ;
        playerSlot = 0 ;
        accountId = 0 ;
                
    }
    /**
     *
     * @return
     * The playerSlot
     */
    public long getPlayerSlot() {
        return playerSlot;
    }

    /**
     *
     * @param playerSlot
     * The player_slot
     */
    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    /**
     *
     * @return
     * The accountId
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     * The account_id
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return
     * The heroId
     */
    public long getHeroId() {
        return heroId;
    }

    /**
     *
     * @param heroId
     * The hero_id
     */
    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    /**
     *
     * @return
     * The kills
     */
    public long getKills() {
        return kills;
    }

    /**
     *
     * @param kills
     * The kills
     */
    public void setKills(long kills) {
        this.kills = kills;
    }

    /**
     *
     * @return
     * The death
     */
    public long getDeath() {
        return death;
    }

    /**
     *
     * @param death
     * The death
     */
    public void setDeath(long death) {
        this.death = death;
    }

    /**
     *
     * @return
     * The assists
     */
    public long getAssists() {
        return assists;
    }

    /**
     *
     * @param assists
     * The assists
     */
    public void setAssists(long assists) {
        this.assists = assists;
    }

    /**
     *
     * @return
     * The lastHits
     */
    public long getLastHits() {
        return lastHits;
    }

    /**
     *
     * @param lastHits
     * The last_hits
     */
    public void setLastHits(long lastHits) {
        this.lastHits = lastHits;
    }

    /**
     *
     * @return
     * The denies
     */
    public long getDenies() {
        return denies;
    }

    /**
     *
     * @param denies
     * The denies
     */
    public void setDenies(long denies) {
        this.denies = denies;
    }

    /**
     *
     * @return
     * The gold
     */
    public long getGold() {
        return gold;
    }

    /**
     *
     * @param gold
     * The gold
     */
    public void setGold(long gold) {
        this.gold = gold;
    }

    /**
     *
     * @return
     * The level
     */
    public long getLevel() {
        return level;
    }

    /**
     *
     * @param level
     * The level
     */
    public void setLevel(long level) {
        this.level = level;
    }

    /**
     *
     * @return
     * The goldPerMin
     */
    public long getGoldPerMin() {
        return goldPerMin;
    }

    /**
     *
     * @param goldPerMin
     * The gold_per_min
     */
    public void setGoldPerMin(long goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    /**
     *
     * @return
     * The xpPerMin
     */
    public long getXpPerMin() {
        return xpPerMin;
    }

    /**
     *
     * @param xpPerMin
     * The xp_per_min
     */
    public void setXpPerMin(long xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    /**
     *
     * @return
     * The ultimateState
     */
    public long getUltimateState() {
        return ultimateState;
    }

    /**
     *
     * @param ultimateState
     * The ultimate_state
     */
    public void setUltimateState(long ultimateState) {
        this.ultimateState = ultimateState;
    }

    /**
     *
     * @return
     * The ultimateCooldown
     */
    public long getUltimateCooldown() {
        return ultimateCooldown;
    }

    /**
     *
     * @param ultimateCooldown
     * The ultimate_cooldown
     */
    public void setUltimateCooldown(long ultimateCooldown) {
        this.ultimateCooldown = ultimateCooldown;
    }

    /**
     *
     * @return
     * The item0
     */
    public long getItem0() {
        return item0;
    }

    /**
     *
     * @param item0
     * The item0
     */
    public void setItem0(long item0) {
        this.item0 = item0;
    }

    /**
     *
     * @return
     * The item1
     */
    public long getItem1() {
        return item1;
    }

    /**
     *
     * @param item1
     * The item1
     */
    public void setItem1(long item1) {
        this.item1 = item1;
    }

    /**
     *
     * @return
     * The item2
     */
    public long getItem2() {
        return item2;
    }

    /**
     *
     * @param item2
     * The item2
     */
    public void setItem2(long item2) {
        this.item2 = item2;
    }

    /**
     *
     * @return
     * The item3
     */
    public long getItem3() {
        return item3;
    }

    /**
     *
     * @param item3
     * The item3
     */
    public void setItem3(long item3) {
        this.item3 = item3;
    }

    /**
     *
     * @return
     * The item4
     */
    public long getItem4() {
        return item4;
    }

    /**
     *
     * @param item4
     * The item4
     */
    public void setItem4(long item4) {
        this.item4 = item4;
    }

    /**
     *
     * @return
     * The item5
     */
    public long getItem5() {
        return item5;
    }

    /**
     *
     * @param item5
     * The item5
     */
    public void setItem5(long item5) {
        this.item5 = item5;
    }

    /**
     *
     * @return
     * The respawnTimer
     */
    public long getRespawnTimer() {
        return respawnTimer;
    }

    /**
     *
     * @param respawnTimer
     * The respawn_timer
     */
    public void setRespawnTimer(long respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    /**
     *
     * @return
     * The positionX
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     *
     * @param positionX
     * The position_x
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     *
     * @return
     * The positionY
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     *
     * @param positionY
     * The position_y
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     *
     * @return
     * The netWorth
     */
    public long getNetWorth() {
        return netWorth;
    }

    /**
     *
     * @param netWorth
     * The net_worth
     */
    public void setNetWorth(long netWorth) {
        this.netWorth = netWorth;
    }

}
