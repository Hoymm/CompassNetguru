package com.kaizen.hoymm.compassnetguru;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.kaizen.hoymm.compassnetguru.LocationPermissions.MY_PERMISSIONS_ACCESS_COARSE_LOCATION;

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
            if (LocationPermissions.isCoarseLocPermissionGranted(getContext())){
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(getContext());


                View latLongPickerView = getLatLngView();
                builderDialog.setView(latLongPickerView);

                builderDialog.setNegativeButton(R.string.cancel, ((dialog, which) -> {}));
                builderDialog.setPositiveButton(R.string.ok, ((dialog, which) -> {}));
                AlertDialog dialog = builderDialog.create();

                dialog.setOnShowListener(dialogInterface -> {

                    Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> {
                        // TODO Do something

                        TextView latText = latLongPickerView.findViewById(R.id.latitudeInput);
                        TextView lngText = latLongPickerView.findViewById(R.id.longitudeInput);
                        TextView errorText = latLongPickerView.findViewById(R.id.errorMessage);
                        String latValue = latText.getText().toString();
                        String lngValue = lngText.getText().toString();

                        try {
                            if (isLatLngInputProper(latValue, lngValue)) {
                                DoublePoint newTarget = new DoublePoint(Double.valueOf(latValue), Double.valueOf(lngValue));
                                refreshCords.setNewTargetLocation(newTarget);
                                dialog.dismiss();
                                errorText.setVisibility(View.GONE);
                            } else {
                                errorText.setVisibility(View.VISIBLE);
                                errorText.setText(getString(R.string.too_big_value));
                            }
                            //Dismiss once everything is OK.
                        }catch (NumberFormatException e){
                            errorText.setVisibility(View.VISIBLE);
                            errorText.setText(getString(R.string.please_user_proper_format_i_e_54_3432));
                        }
                    });
                });
                dialog.show();


                dialog.show();
            }
            else
                LocationPermissions.askForGrantingCoarseLocPermission(getActivity());
        };
    }

    public boolean isLatLngInputProper(String latValue, String lngValue) throws NumberFormatException {
        Double lat, lng;
        lat = Double.valueOf(latValue);
        lng = Double.valueOf(lngValue);
        return lat > -180.0 && lat < 180.0 && lng > -180.0 && lng < 180.0;

    }

    private View getLatLngView() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.lat_lng_picker_dialog, null);
        return dialogView;
    }

    private View.OnClickListener getLongitudeButtonAction() {
        return v -> {
            if (LocationPermissions.isCoarseLocPermissionGranted(getContext()))
                Toast.makeText(getContext(), "Longitude",Toast.LENGTH_SHORT).show();
            else
                LocationPermissions.askForGrantingCoarseLocPermission(getActivity());
        };
    }

    private View.OnClickListener getGoogleMapsButtonAction() {
        return v -> {
            if (LocationPermissions.isCoarseLocPermissionGranted(getContext()))
                Toast.makeText(getContext(), "Google Maps",Toast.LENGTH_SHORT).show();
            else
                LocationPermissions.askForGrantingCoarseLocPermission(getActivity());
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                refreshCords.initLocationClient();
                break;
        }
    }
}
