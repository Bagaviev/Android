package com.example.seventhexample;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class InputUiTest {          // Пошла жаришка из Espresso
    private String stringToBetyped = "Suck my dick, fucking world!";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkEditText() {
//        Note: we can get resources here like that if we need
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        String str = context.getResources().getString(R.string.fragmentTv3);

        onView(withId(R.id.editTextText1))      // Step 1: input some text to editText
                .perform(typeText(stringToBetyped));

        // chaining method don't working and i don`t want to fucking know why
        Espresso.closeSoftKeyboard();
        // exception when keyboard not closed lel )))

        onView(withId(R.id.button3))           // Step 2: trigger button (sets textView with text from previous step)
                .perform(click());

        onView(withId(R.id.textView3))         // Step 3: check equality textView with hardcoded text, then test passes
                .check(matches(withText(stringToBetyped)));

        try {
            Thread.sleep(3000);     // gg wp
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}