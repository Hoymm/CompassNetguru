package com.kaizen.hoymm.compassnetguru;

import android.content.DialogInterface;
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

public class ButtonsFrag extends Fragment {
    private Button setTarget;
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
        setTarget = view.findViewById(R.id.latitude);
        refreshCords = (RefreshCords) getActivity();
    }

    private void setButtonsActions() {
        setTarget.setOnClickListener(getSetTargetButtonAction());
    }

    private View.OnClickListener getSetTargetButtonAction() {
        return v -> {
            if (LocationPermissions.isLocPermissionGranted(getContext())) {
                showDialogToPickTargetPointValues();
            } else
                LocationPermissions.askForGrantingCoarseLocPermission(getActivity());
        };
    }

    void showDialogToPickTargetPointValues() {
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(getContext());
        View latLongPickerView = getLatLngView();
        builderDialog.setView(latLongPickerView);
        builderDialog.setNegativeButton(R.string.cancel, ((dialog, which) -> {
        }));
        builderDialog.setPositiveButton(R.string.ok, ((dialog, which) -> {
        }));
        builderDialog.setTitle(R.string.insert_target_position);
        AlertDialog dialog = builderDialog.create();
        dialog.setOnShowListener(getPossitiveButtonAction(latLongPickerView, dialog));
        dialog.show();
    }

    @NonNull
    private DialogInterface.OnShowListener getPossitiveButtonAction(View latLongPickerView, AlertDialog dialog) {
        return dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                try {
                    tryPorcessWithNewlyInsertedValues(latLongPickerView, dialog);
                } catch (NumberFormatException e) {
                    alertUserToUseProperDoubleNotation(latLongPickerView);
                }
            });
        };
    }

    private void tryPorcessWithNewlyInsertedValues(View latLongPickerView, AlertDialog dialog) {
        TextView latText = latLongPickerView.findViewById(R.id.latitudeInput);
        TextView lngText = latLongPickerView.findViewById(R.id.longitudeInput);
        TextView errorText = latLongPickerView.findViewById(R.id.errorMessage);

        String latValue = latText.getText().toString();
        String lngValue = lngText.getText().toString();
        if (isLatLngInputProper(latValue, lngValue)) {
            DoublePoint newTarget = new DoublePoint(Double.valueOf(latValue), Double.valueOf(lngValue));
            refreshCords.setNewTargetLocation(newTarget);
            dialog.dismiss();
            errorText.setVisibility(View.GONE);
        } else {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(getString(R.string.value_beyond_interval));
        }
    }

    private void alertUserToUseProperDoubleNotation(View latLongPickerView) {
        TextView errorText = latLongPickerView.findViewById(R.id.errorMessage);
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(getString(R.string.please_user_proper_format_i_e_54_3432));
    }

    public boolean isLatLngInputProper(String latValue, String lngValue) throws NumberFormatException {
        Double lat, lng;
        lat = Double.valueOf(latValue);
        lng = Double.valueOf(lngValue);
        return lat >= -90.0 && lat <= 90.0 && lng >= -180.0 && lng <= 180.0;

    }

    private View getLatLngView() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        return layoutInflater.inflate(R.layout.lat_lng_picker_dialog, null);
    }
}
