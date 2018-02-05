package com.kaizen.hoymm.compassnetguru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Damian Muca (Kaizen) on 05.02.18.
 */

public class CompassFrag extends Fragment {
    private Coords targetLocation, yourLocation;
    private ImageView pointerImg, targetImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObjects(view);
        targetLocation = SP_Data.getTargetLocation(getActivity());
    }

    private void initObjects(View view) {
        pointerImg = view.findViewById(R.id.pointer);
        targetImg = view.findViewById(R.id.target);
    }

    public void setTargetLocation(Coords newTargetLocation){
        targetLocation = newTargetLocation;
        SP_Data.saveTargetLocation(targetLocation, getActivity());
    }
}
