package com.example.goodthingscheduler;

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_MOUNTAIN_GREEN = 1;
    public final static int THEME_UNDERWATER = 2;
    public final static int THEME_SNOWY_MOUNTAIN =3;

    //set the theme of the Activity and restart
    public static void changeToTheme(Activity activity, int theme){
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    //set the theme of the Activity, according to the settings
    public static void onActivityCreateSetTheme(Activity activity){
        switch(sTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_GoodThingScheduler);
                break;
            case THEME_MOUNTAIN_GREEN:
                activity.setTheme(R.style.MountainGreen);
                break;
            case  THEME_UNDERWATER:
                activity.setTheme(R.style.Underwater);
                break;
            case THEME_SNOWY_MOUNTAIN:
                activity.setTheme(R.style.SnowyMountain);
                break;
        }
    }

}
