package com.kaizen.hoymm.compassnetguru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CompassFrag compassFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCompassFrag();
    }

    private void addCompassFrag() {
        compassFrag = new CompassFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.compassFrame, compassFrag).commit();
    }
}
