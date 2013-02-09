package com.fourpool.cointoss.android.test;

import android.test.ActivityInstrumentationTestCase2;
import com.fourpool.cointoss.android.*;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class); 
    }

    public void testActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }
}

