package com.paulinakornatowicz.uitestshelper.helpermethods

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

fun executeCommand(command: String) {
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        .executeShellCommand(command)
}
