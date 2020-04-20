package com.paulinakornatowicz.uitestshelper.rules

import androidx.test.espresso.IdlingRegistry
import com.paulinakornatowicz.uitestshelper.idlingresources.Idle
import com.paulinakornatowicz.uitestshelper.idlingresources.DefaultUITestsIdlingResource
import com.paulinakornatowicz.uitestshelper.idlingresources.Busy
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IdlingResourcesRule(val testName: () -> String) : TestWatcher() {

    private lateinit var idlingResource: DefaultUITestsIdlingResource

    override fun starting(description: Description?) {
        idlingResource = DefaultUITestsIdlingResource(testName())

        EventBus.getDefault().register(this)
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        EventBus.getDefault().unregister(this)
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    @Subscribe
    fun onEvent(event: Busy) {
        idlingResource.increment(event.message)
    }

    @Subscribe
    fun onEvent(event: Idle) {
        idlingResource.decrement(event.message)
    }
}