package com.kaizen.hoymm.compassnetguru;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.ImageView;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_ORIENTATION;

class NeedleView implements SensorEventListener {
    private SensorManager mSensorManager;
    private ImageView needle;

    NeedleView(Context context, ImageView needle){
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        this.needle = needle;
    }

    void onResume(){
        // TODO change deprecated Sensor
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
        needle.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        needle.setRotation(-event.values[0]);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void onPause(){
        mSensorManager.unregisterListener(this);
        needle.setLayerType(View.LAYER_TYPE_NONE, null);
    }
}
