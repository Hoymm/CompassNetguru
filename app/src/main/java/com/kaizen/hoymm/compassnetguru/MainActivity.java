package com.kaizen.hoymm.compassnetguru;

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
        getSupportFragmentManager().beginTransaction().add(R.id.buttonsFrame, buttonsFrag).commit();
    }

    private void initAndAddCompassFrag() {
        compassFrag = new CompassFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.compassFrame, compassFrag).commit();
    }

}
