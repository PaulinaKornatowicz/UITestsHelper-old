package com.paulinakornatowicz.uitestshelper.idlingresources

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

// FIXME this should be accessed from another package so that main uitesthelper is a dependency
//  for androidTest only (just like espresso-idlingresources)
interface IdleStateReceiver {

    fun onAppIsBusy(message: String) {
        onIdleStateChanged(Busy(message))
    }

    fun onAppIsIdle(message: String) {
        onIdleStateChanged(Idle(message))
    }

    private fun onIdleStateChanged(state: IdleState) {
        EventBus.getDefault().post(state)
    }

    @Subscribe
    fun onEvent(event: Busy) {
        // no op needed for event bus to work
    }

    @Subscribe
    fun onEvent(event: Idle) {
        // no op needed for event bus to work
    }
}