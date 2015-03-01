package com.bluealeaf.dota2ticker.util;





import android.app.Activity;
import android.content.Intent;

import com.bluealeaf.dota2ticker.SettingsActivity;

/**
 * Created by samidh on 1/3/15.
 */
public class MenuActions {

    public static void openSettings(Activity context) {
        Intent intent = new Intent(context.getApplicationContext(),SettingsActivity.class);
        context.startActivity(intent);
    }
}
