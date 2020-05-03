package com.paulinakornatowicz.uitestshelper.matchers.custom

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

fun assertCustomValue(viewId: Int, matcher: Matcher<View>) {
    onView(withId(viewId)).check(matches(matcher))
}