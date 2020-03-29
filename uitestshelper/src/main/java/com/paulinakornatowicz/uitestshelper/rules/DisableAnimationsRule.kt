package com.paulinakornatowicz.uitestshelper.rules

import com.paulinakornatowicz.uitestshelper.helpermethods.executeCommand
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DisableAnimationsRule: TestWatcher() {

    override fun starting(description: Description?) {
        disableAnimations()
        super.starting(description)
    }

    override fun finished(description: Description?) {
        enableAnimations()
        super.finished(description)
    }

    private fun disableAnimations() {
        executeCommand("settings put global transition_animation_scale 0")
        executeCommand("settings put global window_animation_scale 0")
        executeCommand("settings put global animator_duration_scale 0")
    }

    private fun enableAnimations() {
        executeCommand("settings put global transition_animation_scale 1")
        executeCommand("settings put global window_animation_scale 1")
        executeCommand("settings put global animator_duration_scale 1")
    }
}