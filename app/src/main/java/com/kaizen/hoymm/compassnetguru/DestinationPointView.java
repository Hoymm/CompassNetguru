package com.kaizen.hoymm.compassnetguru;

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

    void onResume() {
        // TODO change deprecated Sensor
        targetImg.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }



    void refreshTargetPositionIfYourAndTargetKnown(DoublePoint from, DoublePoint to) throws NullPointerException {
        if (from != null && to != null)
            try {
                performAnimationSetUpTarget(from, to);
            } catch (PointsAreTheSameException e) {
                e.printStackTrace();
                Log.e("Bearing Angle", "Points given to count bearing are the same.");
            }
    }

    private void performAnimationSetUpTarget(DoublePoint yourPos, DoublePoint targetPos)
            throws PointsAreTheSameException, NullPointerException {

            float rotateFromAngle = targetImg.getRotation();
            float rotateToAngle = tryGetBearingBetweenPoints(yourPos, targetPos) - 90;
            rotateToAngle = findShortestAnglePath(rotateFromAngle, rotateToAngle);

            targetImg.setVisibility(View.VISIBLE);
            Log.i("Img", "Width: " + String.valueOf(targetImg.getWidth()) + ", height: " + String.valueOf(targetImg.getHeight()));
            RotateAnimation rotateAnimation = new RotateAnimation(rotateFromAngle, rotateToAngle,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1700);
            rotateAnimation.setInterpolator(x -> (float) (Math.cos((x + 1) * Math.PI) / 2 + 0.5));
            rotateAnimation.setFillAfter(true);
            targetImg.setAnimation(rotateAnimation);
            targetImg.animate();
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
