package com.kaizen.hoymm.compassnetguru;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
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

    @LargeTest
    public void testPointerImageViewNotNull(){
        ImageView imageView = mActivity.findViewById(R.id.pointer);
        assertNotNull(imageView);
    }

    @After
    public void tearDown() throws Exception{

    }
}
