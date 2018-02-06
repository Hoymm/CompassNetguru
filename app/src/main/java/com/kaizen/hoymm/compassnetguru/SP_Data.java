package com.kaizen.hoymm.compassnetguru;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Damian Muca (Kaizen) on 05.02.18.
 */

class SP_Data {
    final static String TARGET_COORDS_KEY = "com.kaizen.hoymm.compassnetguru.CompassFrag.TARGET_COORDS_KEY";

    static Coords getLastTargetLocation(Activity activity) {
        SharedPreferences mPrefs = activity.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(TARGET_COORDS_KEY, "");
        return gson.fromJson(json, Coords.class);
    }

    static void saveTargetLocation(Coords targetLoc, Activity activity) {
        SharedPreferences  mPrefs = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(targetLoc);
        prefsEditor.putString(TARGET_COORDS_KEY, json);
        prefsEditor.apply();
    }
}
