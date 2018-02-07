package com.kaizen.hoymm.compassnetguru;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

class DestinationPointView {

    private ImageView targetImg;

    DestinationPointView(ImageView triangleTarget) {
        targetImg = triangleTarget;
    }

    void onResume(final Activity activity) {
        // TODO change deprecated Sensor
        performAnimationAfterGraphRendered(activity);
        targetImg.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    private void performAnimationAfterGraphRendered(Activity activity) {
        new Thread(() -> {
            while(isTargetGraphRendered()) {
                isTargetGraphRendered();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isTargetGraphRendered();
            activity.runOnUiThread(this::tryAnimateTargetAtProperPosition);
        }).start();
    }

    private boolean isTargetGraphRendered() {
        return targetImg.getWidth() != 0 && targetImg.getHeight() != 0;
    }

    private void tryAnimateTargetAtProperPosition() {
        DoublePoint cracow = new DoublePoint(50.0647, 19.9450);
        DoublePoint rzeszow = new DoublePoint(50.027504, 22.008011);


        try {
            performAnimationSetUpTarget(cracow, rzeszow);
        } catch (PointsAreTheSameException e) {
            e.printStackTrace();
            Log.e("Bearing Angle", "Points given to count bearing are the same.");
        }
    }

    private void performAnimationSetUpTarget(DoublePoint yourPos, DoublePoint targetPos) throws PointsAreTheSameException {
        float rotateFromAngle = targetImg.getRotation();
        float rotateToAngle = -(tryGetBearingBetweenPoints(yourPos, targetPos) - 90);
        rotateToAngle = findShortestAnglePath(rotateFromAngle, rotateToAngle);

        targetImg.setVisibility(View.VISIBLE);
        Log.i("Img", "Width: " + String.valueOf(targetImg.getWidth()) + ", height: " + String.valueOf(targetImg.getHeight()));
        RotateAnimation rotateAnimation = new RotateAnimation(rotateFromAngle, rotateToAngle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1700);
        rotateAnimation.setInterpolator(x -> (float) (Math.cos((x + 1) * Math.PI) / 2 + 0.5));
        rotateAnimation.setFillAfter(true);
        targetImg.startAnimation(rotateAnimation);
    }

    private float findShortestAnglePath(float rotateFromAngle, float rotateToAngle) {
        if (rotateToAngle - rotateFromAngle > 180)
            rotateToAngle -= 360;
        else if (rotateToAngle - rotateFromAngle < -180)
            rotateToAngle += 360;
        return rotateToAngle;
    }

    void onPause() {
        targetImg.setLayerType(View.LAYER_TYPE_NONE, null);
    }

    float tryGetBearingBetweenPoints(DoublePoint from, DoublePoint to) throws PointsAreTheSameException {
        if (arePointsTheSame(from, to))
            throw new PointsAreTheSameException("Inserted points are the same point.");
        else
            return getBearingBetweenPoints(from, to);
    }

    boolean arePointsTheSame(DoublePoint from, DoublePoint to) {
        return from.latitude == to.latitude && from.longitude == to.longitude;
    }

    float getBearingBetweenPoints(DoublePoint firstPoint, DoublePoint secondPoint) {
        if ((secondPoint.latitude > firstPoint.latitude)) {//above 0 to 180 degrees
            return (float) (Math.atan2((secondPoint.latitude - firstPoint.latitude)
                    , (firstPoint.longitude - secondPoint.longitude)) * 180 / Math.PI);

        } else if ((secondPoint.latitude < firstPoint.latitude)) {//above 180 degrees to 360/0

            return (float) (360 - (Math.atan2((firstPoint.latitude - secondPoint.latitude),
                    (firstPoint.longitude - secondPoint.longitude)) * 180 / Math.PI));

        }
        return (float) Math.atan2(0, 0);

    }


}