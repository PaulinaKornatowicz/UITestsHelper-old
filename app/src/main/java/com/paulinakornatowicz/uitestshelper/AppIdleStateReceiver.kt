package com.paulinakornatowicz.uitestshelper

import com.paulinakornatowicz.uitestshelper.idlingresources.IdleStateReceiver

class AppIdleStateReceiver: IdleStateReceiver {

    fun onBusyOperationStarted() {
        onAppIsBusy("busy operation started")
    }

    fun onBusyOperationEnded() {
        onAppIsIdle("busy operation ended")
    }
}