package com.paulinakornatowicz.uitestshelper.matchers.text

import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*

fun assertText(id: Int, expectedTextValue: String) {
    onView(withId(id)).check(matches(withText(expectedTextValue)))
}

fun assertText(id: Int, expectedTextResource: Int) {
    onView(withId(id)).check(matches(withText(expectedTextResource)))
}

fun assertEditText(expectedTextValue: String) {
    onView(isAssignableFrom(EditText::class.java)).check(matches(withText(expectedTextValue)))
}

fun assertEditText(expectedTextResource: Int) {
    onView(isAssignableFrom(EditText::class.java)).check(matches(withText(expectedTextResource)))
}

fun assertSpinnerText(id: Int, expectedTextValue: String) {
    onView(withId(id)).check(matches(withSpinnerText(containsString(expectedTextValue))))
}

fun assertSpinnerText(id: Int, expectedTextResource: Int) {
    onView(withId(id)).check(matches(withSpinnerText(expectedTextResource)))
}

fun assertSnackbarText(expectedTextValue: String) {
    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(expectedTextValue)))
}

fun assertSnackbarText(expectedTextResource: Int) {
    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(expectedTextResource)))
}

fun assertContainsSnackbarText(expectedTextValue: String) {
    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(containsString(expectedTextValue))))
}

// works for ActionBar with TextView, not Toolbar
fun assertActionBarTitleText(expectedTextValue: String) {
    onView(
        allOf(
            instanceOf(TextView::class.java),
            withParent(withResourceName("action_bar"))
        )
    ).check(matches(withText(expectedTextValue)))
}

// works for ActionBar with TextView, not Toolbar
fun assertActionBarTitleText(expectedTextResource: Int) {
    onView(
        allOf(
            instanceOf(TextView::class.java),
            withParent(withResourceName("action_bar"))
        )
    ).check(matches(withText(expectedTextResource)))
}