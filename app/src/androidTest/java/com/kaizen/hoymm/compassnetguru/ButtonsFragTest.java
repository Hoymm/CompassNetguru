package com.kaizen.hoymm.compassnetguru;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Damian Muca (Kaizen) on 09.02.18.
 */

public class ButtonsFragTest {
    private ButtonsFrag buttonsFragTest;

    @Before
    public void setUp() throws Exception{
        buttonsFragTest = new ButtonsFrag();
    }

    @Test
    public void testIsLatLngInputProperExpectFalse(){
        assertFalse(buttonsFragTest.isLatLngInputProper("180.345","12.23"));
        assertFalse(buttonsFragTest.isLatLngInputProper("10.345","2342.23"));
        assertFalse(buttonsFragTest.isLatLngInputProper("-2310.345","2342.23"));
        assertFalse(buttonsFragTest.isLatLngInputProper("-10.345","-12342.23"));
        assertFalse(buttonsFragTest.isLatLngInputProper("-324235.345","-12342.23"));
        assertFalse(buttonsFragTest.isLatLngInputProper("324235.345","12342.23"));
    }

    @Test
    public void testIsLatLngInputProperExpectTrue(){
        assertTrue(buttonsFragTest.isLatLngInputProper(".3425","23.23"));
        assertTrue(buttonsFragTest.isLatLngInputProper("64.3425",".23"));
        assertTrue(buttonsFragTest.isLatLngInputProper(".3425",".23"));

        assertTrue(buttonsFragTest.isLatLngInputProper("-.3425",".23"));
        assertTrue(buttonsFragTest.isLatLngInputProper(".3425","-.23"));
        assertTrue(buttonsFragTest.isLatLngInputProper("-.3425","-.23"));

        assertTrue(buttonsFragTest.isLatLngInputProper("10.0","0.352523"));
        assertTrue(buttonsFragTest.isLatLngInputProper("10.3525","40.355"));
        assertTrue(buttonsFragTest.isLatLngInputProper("10.3525","-40.355"));
        assertTrue(buttonsFragTest.isLatLngInputProper("-14.3525","40.355"));
        assertTrue(buttonsFragTest.isLatLngInputProper("-10.3525","-40.355"));
    }

    @Test (expected = NumberFormatException.class)
    public void testIsLatLngInputProperException1(){
        buttonsFragTest.isLatLngInputProper("1sds45","12.23");
    }

    @Test (expected = NumberFormatException.class)
    public void testIsLatLngInputProperException2(){
        buttonsFragTest.isLatLngInputProper("123","Co tam");
    }

    @After
    public void tearDown() throws Exception{

    }
}
