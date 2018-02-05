package com.kaizen.hoymm.compassnetguru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Damian Muca (Kaizen) on 05.02.18.
 */

public class ButtonsFrag extends Fragment {
    private Button latitude, longitude, googleMaps;
    private RefreshCords refreshCords;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buttons_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObjects(view);
        setButtonsActions();
    }

    private void initObjects(View view) {
        latitude = view.findViewById(R.id.latitude);
        longitude = view.findViewById(R.id.longitude);
        googleMaps = view.findViewById(R.id.googleMaps);

        refreshCords = (RefreshCords) getActivity();
    }

    private void setButtonsActions() {
        latitude.setOnClickListener(getLatitudeButtonAction());
        longitude.setOnClickListener(getLongitudeButtonAction());
        googleMaps.setOnClickListener(getGoogleMapsButtonAction());
    }

    private View.OnClickListener getLatitudeButtonAction() {
        return v -> {
                Toast.makeText(getContext(), "Latitude",Toast.LENGTH_SHORT).show();
            };
    }

    private View.OnClickListener getLongitudeButtonAction() {
        return v -> {
            Toast.makeText(getContext(), "Longitude",Toast.LENGTH_SHORT).show();
        };
    }

    private View.OnClickListener getGoogleMapsButtonAction() {
        return v -> {
            Toast.makeText(getContext(), "Google Maps",Toast.LENGTH_SHORT).show();
        };
    }
}
