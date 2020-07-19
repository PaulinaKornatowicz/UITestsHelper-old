package com.paulinakornatowicz.uitestshelper.matchers.text

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import org.hamcrest.CoreMatchers.containsString

fun assertText(id: Int, expectedTextValue: String) {
    onView(withId(id)).check(matches(withText(expectedTextValue)))
}

fun assertSpinnerText(id: Int, expectedTextValue: String) {
    onView(withId(id)).check(matches(withSpinnerText(containsString(expectedTextValue))))
}

fun assertSnackbarText(expectedTextValue: String) {
    onView(withId(com.google.android.material.R.id.snackbar_text))
        .check(matches(withText(expectedTextValue)))
}