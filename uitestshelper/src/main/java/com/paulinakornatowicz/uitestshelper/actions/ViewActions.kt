package com.paulinakornatowicz.uitestshelper.actions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId

fun setTextOnView(view: Int, text: String) {
    onView(withId(view)).perform(replaceText(text))
}

fun performClick(view: Int) {
    onView(withId(view)).perform(click())
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

fun setCustomAction(viewId: Int, action: ViewAction) {
    onView(withId(viewId)).perform(action)
}

private fun clickOnChildView(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        click().perform(uiController, view.findViewById<View>(viewId))
}
