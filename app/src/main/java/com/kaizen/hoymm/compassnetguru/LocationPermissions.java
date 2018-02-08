package com.kaizen.hoymm.compassnetguru;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Damian Muca (Kaizen) on 08.02.18.
 */

class LocationPermissions {
    final private static int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;

    void askForGrantingCoarseLocPermission(final Activity activity){
        if (!isCoarseLocPermissionGranted(activity)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION))
                showDialogToExplainWhyAppNeedsPermission(activity);
            else
                askForAccessCoarseLocationPermission(activity);
        }
    }

    boolean isCoarseLocPermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void showDialogToExplainWhyAppNeedsPermission(Activity activity) {
        AlertDialog.Builder explanationDialog = new AlertDialog.Builder(activity);
        explanationDialog.setCancelable(true);
        explanationDialog.setTitle(R.string.loc_perm_req);
        explanationDialog.setMessage(R.string.access_coarse_loc_explanation);
        explanationDialog.setPositiveButton("OK",
                (dialog, which) -> askForAccessCoarseLocationPermission(activity));
        AlertDialog alertDialog = explanationDialog.create();
        alertDialog.show();
    }

    private void askForAccessCoarseLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
    }
}
