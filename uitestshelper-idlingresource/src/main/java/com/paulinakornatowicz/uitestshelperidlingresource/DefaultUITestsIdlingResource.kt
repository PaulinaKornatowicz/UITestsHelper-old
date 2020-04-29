package com.paulinakornatowicz.uitestshelperidlingresource

import android.util.Log
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class DefaultUITestsIdlingResource(private val resourceName: String) : IdlingResource {

    companion object {
        const val TAG = "EspressoIdling"
    }

    private val counter = AtomicInteger(0)

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean {
        return counter.get() == 0
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun increment(message: String = "") = counter.getAndIncrement().apply {
        Log.d(TAG, "increment $counter, message = $message")
    }

    fun decrement(message: String = "") {
        val counterVal = counter.decrementAndGet()
        Log.d(TAG, "decrement after $counter, message = $message")
        if (counterVal == 0) {
            resourceCallback?.onTransitionToIdle()
        }

        if (counterVal < 0) {
            Log.e(TAG, "Counter has been corrupted!")
        }
    }
}