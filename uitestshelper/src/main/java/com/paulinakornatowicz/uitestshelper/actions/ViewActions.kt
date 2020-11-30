package com.paulinakornatowicz.uitestshelper.actions

import android.view.View
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.contrib.PickerActions.setTime
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

fun setTextOnView(view: Int, text: String) {
    onView(withId(view)).perform(replaceText(text))
}

fun setTimeOnTimePicker(hours: Int, minutes: Int) {
    onView(ViewMatchers.isAssignableFrom(TimePicker::class.java)).perform(setTime(hours, minutes))
}

fun performClick(view: Int) {
    onView(withId(view)).perform(click())
}

fun performClickOnViewWithText(text: String) {
    onView(withText(text)).perform(click())
}

fun performClickOnViewWithText(resId: Int) {
    onView(withText(resId)).perform(click())
}

fun performClickOnActionBarMenuItemWithText(titleResId: Int, text: String) {
    onView(
        allOf(
            withId(titleResId),
            withText(text),
            ViewMatchers.isDisplayed()
        )
    ).perform(click())
}

fun performClickOnActionBarMenuItemWithText(titleResId: Int, resId: Int) {
    onView(
        allOf(
            withId(titleResId),
            withText(resId),
            ViewMatchers.isDisplayed()
        )
    ).perform(click())
}

fun performClickOnRecyclerViewItemAtPosition(recyclerId: Int, position: Int) {
    onView(withId(recyclerId)).perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            click()
        )
    )
}

fun performClickOnRecyclerViewItemViewAtPosition(recyclerId: Int, position: Int, viewId: Int) {
    onView(withId(recyclerId))
        .perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickOnChildView(viewId)
            )
        )
}

fun performSwipeActionOnNumberPicker() {
    val view = onView(ViewMatchers.isAssignableFrom(NumberPicker::class.java))
    view.perform(GeneralSwipeAction(Swipe.SLOW, GeneralLocation.TOP_CENTER, GeneralLocation.BOTTOM_CENTER, Press.THUMB))
}

fun setCustomAction(viewId: Int, action: ViewAction) {
    onView(withId(viewId)).perform(action)
}

private fun clickOnChildView(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        click().perform(uiController, view.findViewById<View>(viewId))
}
