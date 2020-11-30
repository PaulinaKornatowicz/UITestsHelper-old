package com.paulinakornatowicz.uitestshelper.matchers.visibility

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.not

// when view's visibility is set to VISIBLE
fun assertViewDisplayed(view: Int) {
    onView(withId(view)).check(matches(isDisplayed()))
}

fun <T: View> assertViewDisplayed(view: Class<T>) {
    onView(isAssignableFrom(view)).check(matches(isDisplayed()))
}

// when view's  visibility is set to INVISIBLE or GONE
fun assertViewNotDisplayed(view: Int) {
    onView(withId(view)).check(matches(not(isDisplayed())))
}

// when view is not present in the hierarchy
fun assertViewDoesNotExist(view: Int) {
    onView(withId(view)).check(doesNotExist())
}

fun <T: View> assertViewDoesNotExist(view: Class<T>) {
    onView(isAssignableFrom(view)).check(doesNotExist())
}

fun assertViewEnabled(view: Int) {
    onView(withId(view)).check(matches((isEnabled())))
}

fun assertViewNotEnabled(view: Int) {
    onView(withId(view)).check(matches(not(isEnabled())))
}

fun assertViewWithTextEnabled(text: String) {
    onView(withText(text)).check(matches(isEnabled()))
}

fun assertViewWithTextEnabled(resId: Int) {
    onView(withText(resId)).check(matches(isEnabled()))
}

fun assertViewWithTextNotEnabled(text: String) {
    onView(withText(text)).check(matches(not(isEnabled())))
}

fun assertViewWithTextNotEnabled(resId: Int) {
    onView(withText(resId)).check(matches(not(isEnabled())))
}
