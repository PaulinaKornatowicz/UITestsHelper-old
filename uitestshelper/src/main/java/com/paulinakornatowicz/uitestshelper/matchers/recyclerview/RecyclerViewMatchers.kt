package com.paulinakornatowicz.uitestshelper.matchers.recyclerview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun assertRecyclerViewSize(id: Int, expectedListSize: Int) {
    onView(withId(id))
        .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(expectedListSize))
    onView(withId(id)).check(matches(withItemCount(expectedListSize)))
}

fun assertTextAtPositionForItemInRecyclerView(
    recyclerId: Int, position: Int, viewId: Int, expectedText: String
) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(
                position, hasDescendant(
                    allOf(withId(viewId), withText(expectedText))
                )
            )
        )
    )
}

fun assertViewDisplayedForItemInRecyclerView(recyclerId: Int, position: Int, viewId: Int) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(position, hasDescendant(allOf(withId(viewId), isDisplayed())))
        )
    )
}

fun assertViewNotDisplayedAtPositionForItemInRecyclerView(
    recyclerId: Int,
    position: Int,
    viewId: Int
) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(position, hasDescendant(allOf(withId(viewId), not(isDisplayed()))))
        )
    )
}

fun assertViewEnabledAtPositionForItemInRecyclerView(recyclerId: Int, position: Int, viewId: Int) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(position, hasDescendant(allOf(withId(viewId), isEnabled())))
        )
    )
}

fun assertViewDisabledAtPositionForItemInRecyclerView(recyclerId: Int, position: Int, viewId: Int) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(position, hasDescendant(allOf(withId(viewId), not(isEnabled()))))
        )
    )
}

fun assertViewCheckedAtPositionForItemInRecyclerView(recyclerId: Int, position: Int, viewId: Int) {
    onView(withId(recyclerId)).check(
        matches(atPosition(position, hasDescendant(allOf(withId(viewId), isChecked()))))
    )
}

fun assertViewNotCheckedAtPositionForItemInRecyclerView(
    recyclerId: Int,
    position: Int,
    viewId: Int
) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(position, hasDescendant(allOf(withId(viewId), not(isChecked()))))
        )
    )
}

fun assertDrawableAtPositionForItemInRecyclerView(
    recyclerId: Int, position: Int, viewId: Int, drawable: Int, drawableColor: Int? = null
) {
    onView(withId(recyclerId)).check(
        matches(
            atPosition(
                position,
                hasDescendant(allOf(withId(viewId), withDrawable(drawable, drawableColor)))
            )
        )
    )
}

private fun withItemCount(count: Int): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText(count.toString())
        }

        override fun matchesSafely(recycler: RecyclerView?): Boolean =
            count == recycler?.adapter?.itemCount
    }

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("$itemMatcher at position $position")
        }

        override fun matchesSafely(recycler: RecyclerView?): Boolean =
            itemMatcher.matches(recycler?.findViewHolderForAdapterPosition(position)?.itemView)
    }

fun withDrawable(expectedResourceId: Int, withColor: Int? = null): Matcher<View> =
    object : TypeSafeMatcher<View>() {

        var resourceName: String? = null
        private val EMPTY = -1
        private val ANY = -2

        override fun describeTo(description: Description?) {
            description?.apply {
                appendText("with drawable from resource id: ")
                appendValue(expectedResourceId)
                appendText("[$resourceName]")
            }
        }

        override fun matchesSafely(target: View?): Boolean {
            if (target !is ImageView) return false

            when (expectedResourceId) {
                EMPTY -> return target.drawable == null
                ANY -> return target.drawable != null
            }

            resourceName = target.context.resources.getResourceEntryName(expectedResourceId)

            val expectedDrawable =
                ContextCompat.getDrawable(target.context, expectedResourceId) ?: return false
            withColor?.apply {
                expectedDrawable.setColorFilter(
                    ContextCompat.getColor(target.context, this),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }

            val actualDrawable = target.drawable

            when (expectedDrawable) {
                is VectorDrawable -> {
                    if (actualDrawable !is VectorDrawable) return false
                    return getBitmap(expectedDrawable)?.sameAs(getBitmap(actualDrawable)) ?: false
                }

                is BitmapDrawable -> {
                    if (actualDrawable !is BitmapDrawable) return false
                    return expectedDrawable.bitmap.sameAs(actualDrawable.bitmap)
                }

                else -> throw IllegalArgumentException("Unsupported drawable ${target.drawable}")
            }
        }
    }

private fun getBitmap(drawable: Drawable): Bitmap? {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}