package com.example.seventhexample;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class PrefsInstrumentedTest {
    static String testString = "abc";

    @Test
    public void useAppContext() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("testPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(testString, testString)
                .commit();

        String result = sharedPreferences.getString(testString, "");
         assertEquals("abc", result);
    }
}