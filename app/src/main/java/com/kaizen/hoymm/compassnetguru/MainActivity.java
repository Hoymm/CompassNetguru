package com.kaizen.hoymm.compassnetguru;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity implements RefreshCords {
    private CompassFrag compassFrag;
    private ButtonsFrag buttonsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAndAddFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initAndAddFragments() {
        initAndAddButtonsFrag();
        initAndAddCompassFrag();
    }

    private void initAndAddButtonsFrag() {
        buttonsFrag = new ButtonsFrag();
        addFrag(R.id.buttonsFrame, buttonsFrag);
    }

    private void initAndAddCompassFrag() {
        compassFrag = new CompassFrag();
        addFrag(R.id.compassFrame, compassFrag);
    }

    private void addFrag(int viewId, Fragment frag) {
        getSupportFragmentManager().beginTransaction().add(viewId, frag).commit();
    }

    @Override
    public void setNewTargetLocation(DoublePoint newTargetLocation) {
        compassFrag.setTargetLocation(newTargetLocation);
    }
}
