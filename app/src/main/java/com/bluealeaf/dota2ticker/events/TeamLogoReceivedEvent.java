package com.bluealeaf.dota2ticker.events;


import com.bluealeaf.dota2ticker.models.Team_img;

/**
 * Created by samidh on 6/5/15.
 */
public class TeamLogoReceivedEvent {

    private Team_img image;
    int position;
    String team;

    public TeamLogoReceivedEvent(Team_img image, int position, String team){
        this.image = image;
        this.position = position;
        this.team = team;
    }

    public String getImageUrl(){
        return getImage().getUrl();
    }

    public int getPosition(){
        return position;
    }

    public String getTeam(){
        return team;
    }

    public Team_img getImage() {
        return image;
    }

}
