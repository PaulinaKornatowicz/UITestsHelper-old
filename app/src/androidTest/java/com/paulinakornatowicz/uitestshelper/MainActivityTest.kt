package com.paulinakornatowicz.uitestshelper

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.paulinakornatowicz.uitestshelper.rules.DisableAnimationsRule
import com.paulinakornatowicz.uitestshelper.rules.IdlingResourcesRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val disableAnimationsRule = DisableAnimationsRule()

    @Rule
    @JvmField
    val idleResourcesRule = IdlingResourcesRule {
        "MainActivityTest"
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun beforeSetup() {
        activityRule.launchActivity(
            Intent(
                ApplicationProvider.getApplicationContext(),
                MainActivity::class.java
            )
        )
    }


    @Test
    fun mainActivityTest() {
        onView(withText("Hello World!")).check(matches(isDisplayed()))
    }

    @After
    fun afterSetup() {
        activityRule.finishActivity()
    }
}