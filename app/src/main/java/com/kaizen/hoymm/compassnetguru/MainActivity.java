package com.kaizen.hoymm.compassnetguru;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static com.kaizen.hoymm.compassnetguru.LocationPermissions.MY_PERMISSIONS_ACCESS_LOCATION;

public class MainActivity extends FragmentActivity implements RefreshCords {
    private CompassFrag compassFrag;
    private ButtonsFrag buttonsFrag;
    private HeaderFrag headerFrag;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAndAddFragments();
        initLocationClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initAndAddFragments() {
        initAndAddHeaderFrag();
        initAndAddButtonsFrag();
        initAndAddCompassFrag();
    }

    private void initAndAddHeaderFrag() {
        headerFrag = new HeaderFrag();
        addFrag(R.id.headerFrag, headerFrag);
    }

    private void initAndAddButtonsFrag() {
        buttonsFrag = new ButtonsFrag();
        addFrag(R.id.buttonsFrag, buttonsFrag);
    }

    private void initAndAddCompassFrag() {
        compassFrag = new CompassFrag();
        addFrag(R.id.compassFrag, compassFrag);
    }

    private void addFrag(int viewId, Fragment frag) {
        getSupportFragmentManager().beginTransaction().add(viewId, frag).commit();
    }

    @Override
    public void setNewTargetLocation(DoublePoint newTargetLocation) {
        compassFrag.setTargetLocation(newTargetLocation);
        headerFrag.setTargetLocation(newTargetLocation);
    }

    @Override
    public void initLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(LocationPermissions.isLocPermissionGranted(this)) {
            // Permission is checked, inside If statement, AS is lying about warning
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, (location) -> {
                Log.i("Location", "your location has been determined.");
                if (location != null) {
                    headerFrag.setYourLocation(location);
                    compassFrag.setYourLocationAndRefreshGraph(location);

                }
                else{
                    headerFrag.setYourLocationUnknown();
                }
            });
        }
        else{
            Log.i("Location", "permission not granted.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationClient();
                    buttonsFrag.showDialogToPickTargetPointValues();
                }
                break;
        }
    }
}
