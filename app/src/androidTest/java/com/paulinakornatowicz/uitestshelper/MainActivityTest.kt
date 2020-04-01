package com.paulinakornatowicz.uitestshelper

import com.paulinakornatowicz.uitestshelper.rules.DisableAnimationsRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    val disableAnimationsRule = DisableAnimationsRule()

    @Test
    fun test() {
        Thread.sleep(1000)
    }
}