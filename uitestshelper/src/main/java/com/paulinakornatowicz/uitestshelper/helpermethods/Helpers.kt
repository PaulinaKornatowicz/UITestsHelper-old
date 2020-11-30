package com.paulinakornatowicz.uitestshelper.helpermethods

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

fun executeCommand(command: String) {
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        .executeShellCommand(command)
}

//    context for test resources
fun getInstrumentationContext(): Context = InstrumentationRegistry.getInstrumentation().context