package com.paulinakornatowicz.uitestshelper.matchers.visibility

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import org.hamcrest.Matchers.not

fun assertViewDisplayed(view: Int) {
    onView(withId(view)).check(matches(isDisplayed()))
}

fun assertViewNotDisplayed(view: Int) {
    onView(withId(view)).check(matches(not(isDisplayed())))
}

fun assertViewEnabled(view: Int) {
    onView(withId(view)).check(matches((isEnabled())))
}

fun assertViewNotEnabled(view: Int) {
    onView(withId(view)).check(matches(not(isEnabled())))
}
