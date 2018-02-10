package com.kaizen.hoymm.compassnetguru;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DestinationPointViewTest {
    private DoublePoint samplePoint = new DoublePoint(23.2321123, 42.23131);
    private DoublePoint samplePoint2 = new DoublePoint(12.24003, 1.2531);

    private DestinationPointView destinationPointView;

    @Before
    public void setUp() throws Exception {
        destinationPointView = new DestinationPointView(imageView);
    }

    @org.mockito.Mock
    ImageView imageView;

    @Test(expected = PointsAreTheSameException.class)
    public void testExceptionThrowTryGetTargetImgAngle() throws PointsAreTheSameException{
        destinationPointView.tryGetBearingBetweenPoints(samplePoint, samplePoint);
    }

    @Test
    public void testProperlyTryGetTargetImgAngle() throws PointsAreTheSameException{
        destinationPointView.tryGetBearingBetweenPoints(samplePoint, samplePoint2);
    }

    @Test
    public void testArePointsTheSame(){
        assertTrue(destinationPointView.arePointsTheSame(samplePoint, samplePoint));
        assertTrue(destinationPointView.arePointsTheSame(new DoublePoint(1.232, 1.1), new DoublePoint(1.232, 1.1)));
        assertFalse(destinationPointView.arePointsTheSame(samplePoint, samplePoint2));
        assertFalse(destinationPointView.arePointsTheSame(new DoublePoint(1.0, 2.0), new DoublePoint(1.0, 3.0)));
        assertFalse(destinationPointView.arePointsTheSame(new DoublePoint(2.0, 1.0), new DoublePoint(3.0, 1.0)));
        assertFalse(destinationPointView.arePointsTheSame(new DoublePoint(3.4, 1.2), new DoublePoint(23.5, 15.3)));
    }

    @Test
    public void testGetTargetImgAngleTest1(){
        DoublePoint from = new DoublePoint(50.0, 11.0);
        DoublePoint to = new DoublePoint(50.0, 37.0);
        float actual = destinationPointView.getBearingBetweenPoints(from, to);
        float expected = 360.0f % 360.0f;
        assertEquals(expected, actual, 0.1f);
    }

    @Test
    public void testGetTargetImgAngleTest2(){
        DoublePoint from = new DoublePoint(16.0, 20.0);
        DoublePoint to = new DoublePoint(32.0, 20.0);
        float actual = destinationPointView.getBearingBetweenPoints(from, to);
        float expected = 90.0f;
        assertEquals(expected, actual, 0.1f);
    }

    @Test
    public void testGetTargetImgAngleTest3(){
        DoublePoint from = new DoublePoint(10.0, 10.0);
        DoublePoint to = new DoublePoint(20.0, 20.0);
        float actual = destinationPointView.getBearingBetweenPoints(from, to);
        float expected = 135.0f;
        assertEquals(expected, actual, 0.1f);
    }

    @Test
    public void testGetTargetImgAngleTest4(){
        DoublePoint from = new DoublePoint(20.0, 20.0);
        DoublePoint to = new DoublePoint(10.0, 10.0);
        float actual = destinationPointView.getBearingBetweenPoints(from, to);
        float expected = 315.0f;
        assertEquals(expected, actual, 0.1f);
    }

    @Test
    public void testGetTargetImgAngleTest5(){
        DoublePoint from = new DoublePoint(20.0, 20.0);
        DoublePoint to = new DoublePoint(20.0, 10.0);
        float actual = destinationPointView.getBearingBetweenPoints(from, to);
        float expected = 0f;
        assertEquals(expected, actual, 0.1f);
    }

    @Test (expected = NullPointerException.class)
    public void refreshTargetPositionIfYourAndTargetKnownTest_ExpectNullPtrExpection1(){
        destinationPointView.tryRefreshTargetPositionIfYourAndTargetKnown(null, new DoublePoint(23.2, 23.1));
    }

    @Test (expected = NullPointerException.class)
    public void refreshTargetPositionIfYourAndTargetKnownTest_ExpectNullPtrExpection2(){
        destinationPointView.tryRefreshTargetPositionIfYourAndTargetKnown(new DoublePoint(23.2, 23.1), null);
    }

    @Test (expected = NullPointerException.class)
    public void refreshTargetPositionIfYourAndTargetKnownTest_ExpectNullPtrExpection3(){
        destinationPointView.tryRefreshTargetPositionIfYourAndTargetKnown(null, null);
    }

    @After
    public void tearDown() throws Exception {
    }
}
