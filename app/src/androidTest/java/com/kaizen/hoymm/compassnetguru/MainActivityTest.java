package com.kaizen.hoymm.compassnetguru;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testPointerImageViewNotNull(){
        ImageView textView = (ImageView) mActivity.findViewById(R.id.pointer);
        assertNotNull(textView);
    }

    @After
    public void tearDown() throws Exception{

    }
}
