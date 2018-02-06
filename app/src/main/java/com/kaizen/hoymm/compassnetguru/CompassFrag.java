package com.kaizen.hoymm.compassnetguru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Damian Muca (Kaizen) on 05.02.18.
 */

public class CompassFrag extends Fragment {
    private Coords targetLocation, yourLocation;
    private Needle needle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObjects(view);
        targetLocation = SP_Data.getLastTargetLocation(getActivity());

    }

    private void initObjects(View view) {
        initNeedle(view);
    }

    private void initNeedle(View view) {
        needle = new Needle(getContext(), view.findViewById(R.id.pointer));
    }

    public void setTargetLocation(Coords newTargetLocation){
        targetLocation = newTargetLocation;
        SP_Data.saveTargetLocation(targetLocation, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        needle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        needle.onPause();
    }
}
