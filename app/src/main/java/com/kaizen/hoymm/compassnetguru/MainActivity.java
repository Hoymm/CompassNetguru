package com.kaizen.hoymm.compassnetguru;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements RefreshCords {
    private CompassFrag compassFrag;
    private ButtonsFrag buttonsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);
        initAndAddFragments();
    }


    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initAndAddFragments() {
        initAndAddCompassFrag();
        initAndAddButtonsFrag();
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
