package com.bluealeaf.dota2ticker.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.bluealeaf.dota2ticker.MatchDetailsActivity;
import com.bluealeaf.dota2ticker.R;

import greendao.Match;

/**
 * Created by samidh on 23/1/15.
 */
public class NotificationActivity extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {

            Match match = intent.getParcelableExtra("MATCH_ID");

            Intent matchDetailsIntent = new Intent(context, MatchDetailsActivity.class);
            matchDetailsIntent.putExtra("MATCH_ID", match);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, safeLongToInt(match.getId())+65535, matchDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification;
            if (Build.VERSION.SDK_INT >= 16) {
                notification = new Notification.Builder(context)
                        .setContentTitle(match.getT1() + " vs. " + match.getT2())
                        .setSmallIcon(R.drawable.abc_ab_share_pack_holo_dark)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

            } else {
                notification = new Notification.Builder(context).getNotification();
            }

            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(safeLongToInt(match.getId())+65535, notification);

        }
    }

    public int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }



}
