package com.kaizen.hoymm.compassnetguru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompassFrag extends Fragment {
    private DoublePoint targetLocation, yourLocation;
    private DestinationPointView destinationPointView;
    private NeedleView needleView;

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
        destinationPointView.tryAnimateTargetAtProperPosition();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initObjects(View view) {
        initNeedle(view);
        initTargetPoint(view);
    }

    private void initNeedle(View view) {
        needleView = new NeedleView(getContext(), view.findViewById(R.id.pointer));
    }

    private void initTargetPoint(View view) {
        destinationPointView = new DestinationPointView(view.findViewById(R.id.target));
    }

    public void setTargetLocation(DoublePoint newTargetLocation){
        targetLocation = newTargetLocation;
        SP_Data.saveTargetLocation(targetLocation, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        needleView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        needleView.onPause();
        destinationPointView.onPause();
    }
}
