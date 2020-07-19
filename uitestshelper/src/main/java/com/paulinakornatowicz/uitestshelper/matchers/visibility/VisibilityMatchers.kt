package com.paulinakornatowicz.uitestshelper.matchers.visibility

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import org.hamcrest.Matchers.not

// when view's visibility is set to VISIBLE
fun assertViewDisplayed(view: Int) {
    onView(withId(view)).check(matches(isDisplayed()))
}

// when view's  visibility is set to INVISIBLE or GONE
fun assertViewNotDisplayed(view: Int) {
    onView(withId(view)).check(matches(not(isDisplayed())))
}

// when view is not present in the hierarchy
fun assertViewDoesNotExist(view: Int) {
    onView(withId(view)).check(doesNotExist())
}

fun assertViewEnabled(view: Int) {
    onView(withId(view)).check(matches((isEnabled())))
}

fun assertViewNotEnabled(view: Int) {
    onView(withId(view)).check(matches(not(isEnabled())))
}
