package com.kaizen.hoymm.compassnetguru;

import android.location.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompassFragTest {
    private CompassFrag compassFrag;

    @Before
    public void setUp(){
        compassFrag = new CompassFrag();
    }

    @Test
    public void testSetTargetLocation(){
        compassFrag.setTargetLocation(new DoublePoint(0.23, 32.23));
    }

    @Test
    public void testSetTargetLocation_ExpectionHandled(){
        compassFrag.setTargetLocation(null);
    }

    @Test  (expected = NullPointerException.class)
    public void testSetYourLocation_Expection1(){
        Location location = new Location("test");
        compassFrag.setYourLocationAndRefreshGraph(location);
    }

    @Test (expected = NullPointerException.class)
    public void testSetYourLocation_Exception2(){
        compassFrag.setYourLocationAndRefreshGraph(null);
    }


    @After
    public void tearDown(){

    }
}
