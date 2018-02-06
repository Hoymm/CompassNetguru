package com.kaizen.hoymm.compassnetguru;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Damian Muca (Kaizen) on 06.02.18.
 */

public class Needle implements SensorEventListener {
    private SensorManager mSensorManager;
    private ImageView needle;

    Needle(Context context, ImageView needle){
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        this.needle = needle;
    }

    void onResume(){
        // TODO change deprecated Sensor
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
        needle.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        Log.i("Degree", String.valueOf(-degree));
        needle.setRotation(-degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void onPause(){
        mSensorManager.unregisterListener(this);
        needle.setLayerType(View.LAYER_TYPE_NONE, null);
    }
}
