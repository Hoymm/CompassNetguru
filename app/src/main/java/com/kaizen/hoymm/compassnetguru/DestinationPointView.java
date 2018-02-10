package com.kaizen.hoymm.compassnetguru;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

class DestinationPointView {
    private static float lastTargetDegree = 0f;
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
        if (targetImg.getVisibility() == View.INVISIBLE){
            showTargetThenPerformRotate(yourPos, targetPos);
        }
        else{
            performRotateAnimation(yourPos, targetPos);
        }
    }

    private void showTargetThenPerformRotate(DoublePoint yourPos, DoublePoint targetPos) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1600);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                targetImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    performRotateAnimation(yourPos, targetPos);
                } catch (PointsAreTheSameException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        targetImg.setAnimation(alphaAnimation);
        targetImg.animate();
    }

    private void performRotateAnimation(DoublePoint yourPos, DoublePoint targetPos) throws PointsAreTheSameException {
        float rotateFromAngle = lastTargetDegree;
        float rotateToAngle = tryGetBearingBetweenPoints(yourPos, targetPos) - 90;
        lastTargetDegree = rotateToAngle;

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
