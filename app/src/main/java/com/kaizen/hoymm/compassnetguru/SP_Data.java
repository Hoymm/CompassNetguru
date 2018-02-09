package com.kaizen.hoymm.compassnetguru;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

class SP_Data {
    private final static String TARGET_COORDS_KEY = "com.kaizen.hoymm.compassnetguru.CompassFrag.TARGET_COORDS_KEY";

    static DoublePoint getLastTargetLocation(Activity activity) {
        SharedPreferences mPrefs = activity.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(TARGET_COORDS_KEY, "");
        return gson.fromJson(json, DoublePoint.class);
    }

    static void saveTargetLocation(DoublePoint targetLoc, Activity activity) {
        try {
            SharedPreferences mPrefs = activity.getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(targetLoc);
            prefsEditor.putString(TARGET_COORDS_KEY, json);
            prefsEditor.apply();
        }catch (NullPointerException e){
            Log.e("Save Target Location", " probably values of targetLoc was null.");
            e.printStackTrace();
        }
    }
}
