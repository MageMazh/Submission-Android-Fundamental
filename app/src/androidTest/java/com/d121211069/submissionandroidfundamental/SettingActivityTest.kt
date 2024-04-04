package com.d121211069.submissionandroidfundamental

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.d121211069.submissionandroidfundamental.ui.SettingActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun clickThemeSetting() {
        onView(withId(R.id.switch_theme)).check(matches(withText("light mode")))

        onView(withId(R.id.switch_theme)).perform(click())

        onView(withId(R.id.switch_theme)).check(matches(withText("dark mode")))
    }
}