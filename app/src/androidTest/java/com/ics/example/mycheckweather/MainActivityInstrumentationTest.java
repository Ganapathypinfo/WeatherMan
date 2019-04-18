package com.ics.example.mycheckweather;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.ics.example.mycheckweather.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
// Tests for MainActivity
public class MainActivityInstrumentationTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    // Looks for an EditText with id = "R.id.etInput"
    // Types the text "Hello" into the EditText
    // Verifies the EditText has text "Hello"
    @Test
    public void validateTextViews(){

        onView(withId(R.id.city_text)).perform(typeText("Hello City"))
                .check(matches(withText("Hello City")));
        onView(withId(R.id.latitude_text)).perform(typeText("Hello latitude"))
                .check(matches(withText("Hello latitude")));
        onView(withId(R.id.latitude_text)).perform(typeText("Hello latitude"))
                .check(matches(withText("Hello latitude")));
        onView(withId(R.id.te_today_weather)).perform(typeText("Hello today weather"))
                .check(matches(withText("Hello today weather")));
        onView(withId(R.id.te_fst_day_wetr)).perform(typeText("Hello first day weather"))
                .check(matches(withText("Hello first day weather")));
        onView(withId(R.id.te_sec_day_wetr)).perform(typeText("Hello 2nd day weather"))
                .check(matches(withText("Hello 2nd day weather")));
        onView(withId(R.id.te_third_day_wetr)).perform(typeText("Hello 3rd day weather"))
                .check(matches(withText("Hello 3rd day weather")));
    }
}