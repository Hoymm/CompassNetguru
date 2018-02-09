package com.kaizen.hoymm.compassnetguru;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Damian Muca (Kaizen) on 08.02.18.
 */

public class HeaderFrag extends Fragment {
    private static final DecimalFormat txtFormat = new DecimalFormat("#.###");
    private TextView yourLocTV, tarLocTV;
    private ImageView locImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.header_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yourLocTV = view.findViewById(R.id.yourLocationParameters);
        tarLocTV = view.findViewById(R.id.tarLocationParameters);
        locImg = view.findViewById(R.id.location_img);
    }

    void setYourLocation(Location location){
        yourLocTV.setText("Lat: " + txtFormat.format(location.getLatitude())
                + ", Lng: " + txtFormat.format(location.getLongitude()));
        locImg.setImageResource(R.drawable.ic_gps_located);
    }

    void setYourLocationUnknown(){
        yourLocTV.setText("Unknown");
    }

    void setTargetLocation(DoublePoint doublePoint){
        tarLocTV.setText("Lat: " + txtFormat.format(doublePoint.latitude) + ", Lng: "
                + txtFormat.format(doublePoint.longitude));
        locImg.setImageResource(R.drawable.ic_gps_unlocated);
    }
}
