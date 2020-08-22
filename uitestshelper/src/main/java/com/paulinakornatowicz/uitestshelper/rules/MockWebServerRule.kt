package com.paulinakornatowicz.uitestshelper.rules

import com.paulinakornatowicz.uitestshelper.helpermethods.MockServerHelper
import org.junit.rules.TestWatcher
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.runner.Description

class MockWebServerRule(val testName: () -> String, dispatcher: Dispatcher? = null): TestWatcher() {

    private val server = MockWebServer()
    private val serverPort = 8443

    private val serverDispatcher = dispatcher ?: object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            MockServerHelper.dispatch(testName(), request)
    }

    override fun starting(description: Description?) {
        server.dispatcher = serverDispatcher
        server.start(serverPort)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        server.close()
        super.finished(description)
    }
}
