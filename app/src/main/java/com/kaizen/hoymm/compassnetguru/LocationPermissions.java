package com.kaizen.hoymm.compassnetguru;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.location.LocationServices;

/**
 * Created by Damian Muca (Kaizen) on 08.02.18.
 */

class LocationPermissions {
    final static int MY_PERMISSIONS_ACCESS_LOCATION = 1;

    static void askForGrantingCoarseLocPermission(final Activity activity){
        if (!isLocPermissionGranted(activity)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION))
                showDialogToExplainWhyAppNeedsPermission(activity);
            else
                askForLocationPermission(activity);
        }
    }

    static boolean isLocPermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ;
    }

    private static void showDialogToExplainWhyAppNeedsPermission(Activity activity) {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(activity);
        explanationDialog.setCancelable(true);
        explanationDialog.setTitle(R.string.loc_perm_req);
        explanationDialog.setMessage(R.string.access_coarse_loc_explanation);
        explanationDialog.setPositiveButton("OK",
                (dialog, which) -> askForLocationPermission(activity));
        AlertDialog alertDialog = explanationDialog.create();
        alertDialog.show();
    }

    private static void askForLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_LOCATION);
    }

}
