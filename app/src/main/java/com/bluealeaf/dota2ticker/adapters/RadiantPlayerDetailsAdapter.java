package com.bluealeaf.dota2ticker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluealeaf.dota2ticker.R;
import com.bluealeaf.dota2ticker.bus.BusProvider;
import com.bluealeaf.dota2ticker.models.game.Player;
import com.bluealeaf.dota2ticker.models.game.Player_;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by samidh on 4/5/15.
 */
public class RadiantPlayerDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<Player_> players;
    private List<Player> allPlayers;

    private static final String heroImgURL = "http://cdn.dota2.com/apps/dota2/images/heroes/";
    private static final String itemImgURL = "http://cdn.dota2.com/apps/dota2/images/items/";

    public RadiantPlayerDetailsAdapter(Context context,List<Player_> players){
        this.context = context;
        this.players = players;
        allPlayers = BusProvider.game.getPlayers();
    }

    static class ViewHolder{
        TextView GPM;
        TextView XPM;
        TextView playerName;
        TextView currentGold;
        TextView currentLevel;

        ImageView heroImage;
        ImageView item0;
        ImageView item1;
        ImageView item2;
        ImageView item3;
        ImageView item4;
        ImageView item5;

        TextView KDA ;
        TextView LHD ;


    }


    @Override
    public int getCount() {
        return players.size();
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
        ViewHolder viewHolder;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.hero_details_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.GPM = (TextView) view.findViewById(R.id.gpm);
            viewHolder.XPM = (TextView) view.findViewById(R.id.xpm);
            viewHolder.currentGold = (TextView) view.findViewById(R.id.current_gold);
            viewHolder.currentLevel = (TextView) view.findViewById(R.id.current_level);
            viewHolder.playerName = (TextView) view.findViewById(R.id.player_name);
            viewHolder.KDA = (TextView) view.findViewById(R.id.kda);
            viewHolder.LHD = (TextView) view.findViewById(R.id.lhd);

            viewHolder.item0 = (ImageView) view.findViewById(R.id.item0);
            viewHolder.item1 = (ImageView) view.findViewById(R.id.item1);
            viewHolder.item2 = (ImageView) view.findViewById(R.id.item2);
            viewHolder.item3 = (ImageView) view.findViewById(R.id.item3);
            viewHolder.item4 = (ImageView) view.findViewById(R.id.item4);
            viewHolder.item5 = (ImageView) view.findViewById(R.id.item5);


            viewHolder.heroImage = (ImageView) view.findViewById(R.id.hero_img);
            view.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.GPM.setText(String.valueOf(players.get(position).getGoldPerMin()));
        viewHolder.XPM.setText(String.valueOf(players.get(position).getXpPerMin()));
        viewHolder.currentGold.setText(String.valueOf(players.get(position).getGold()));
        viewHolder.currentLevel.setText(String.valueOf(players.get(position).getLevel()));
        long heroID = players.get(position).getHeroId();
        for(Player player : allPlayers){
            if(heroID == player.getHeroId()){
                viewHolder.playerName.setText(player.getName());
                break;
            }
        }

        viewHolder.KDA.setText(String.valueOf(players.get(position).getKills()+"/"+players.get(position).getDeath()+"/"+players.get(position).getAssists()));
        viewHolder.LHD.setText(String.valueOf(players.get(position).getLastHits() + "/" + players.get(position).getDenies()));


        String heroName = context.getString(context.getResources().getIdentifier("hero_"+heroID,"string","com.bluealeaf.dota2ticker")).split("/")[0];
        Picasso.with(context).load(heroImgURL+heroName+"_sb.png").into(viewHolder.heroImage);

        String item0 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem0(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL + item0.split("/")[0] + "_lg.png").into(viewHolder.item0);


        String item1 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem1(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL + item1.split("/")[0] + "_lg.png").into(viewHolder.item1);


        String item2 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem2(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL + item2.split("/")[0] + "_lg.png").into(viewHolder.item2);


        String item3 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem3(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL + item3.split("/")[0] + "_lg.png").into(viewHolder.item3);


        String item4 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem4(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL+item4.split("/")[0]+"_lg.png").into(viewHolder.item4);


        String item5 = context.getString(context.getResources().getIdentifier("item_"+players.get(position).getItem5(),"string","com.bluealeaf.dota2ticker"));
        Picasso.with(context).load(itemImgURL + item5.split("/")[0] + "_lg.png").into(viewHolder.item5);



        return view;
    }
}
