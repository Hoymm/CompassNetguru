package com.kaizen.hoymm.compassnetguru;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CompassFrag compassFrag;
    private ButtonsFrag buttonsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAndAddFragments();
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
}
