package com.paulinakornatowicz.uitestshelperdemo

import com.paulinakornatowicz.uitestshelperidlingresource.IdleStateReceiver

class AppIdleStateReceiver: IdleStateReceiver {

    fun onBusyOperationStarted() {
        onAppIsBusy("busy operation started")
    }

    fun onBusyOperationEnded() {
        onAppIsIdle("busy operation ended")
    }
}