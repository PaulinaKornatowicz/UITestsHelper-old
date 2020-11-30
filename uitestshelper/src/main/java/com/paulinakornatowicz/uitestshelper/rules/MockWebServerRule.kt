package com.paulinakornatowicz.uitestshelper.rules

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.rules.TestWatcher
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.runner.Description
import java.util.concurrent.TimeUnit

class MockServerRule(private val testName: () -> String, dispatcher: Dispatcher? = null): TestWatcher() {

    var mockedServer: List<ServerRequest> = emptyList()

    private var fileName: String = ""

    @JsonClass(generateAdapter = true)
    data class MockServerData(val testName: List<String>, val requests: List<ServerRequest>)

    @JsonClass(generateAdapter = true)
    data class ServerRequest(val url: String, val responses: List<ServerResponse>, var requestCount: Int = 0) {
        fun getAndIncrementRequestCount(): Int {
            val previousCount = requestCount
            requestCount++
            return previousCount
        }
    }

    @JsonClass(generateAdapter = true)
    data class ServerResponse(val responseCode: Int, val responseBody: String, val delay: Int = 0)

    fun setupMockServerData(file: String = "mock.json") {
        fileName = file
        val fileContent = readFile(file)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<List<MockServerData>> = moshi.adapter(Types.newParameterizedType(MutableList::class.java, MockServerData::class.java))
        val json = adapter.fromJson(fileContent)
        val defaultList = json!!.find { it.testName.contains("default")}
        val testList = json.find { it.testName.contains(testName())}
        mockedServer = (testList?.requests?.plus(defaultList!!.requests)?.distinctBy { it.url } ?: defaultList!!.requests)
    }

    private val server = MockWebServer()
    private val serverPort = 8443

    private val serverDispatcher = dispatcher ?: object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            Assert.assertTrue(
                "Mocked data empty, did you call setupMockServerData in @Before method of your tests?",
                mockedServer.isNotEmpty()
            )
            val path = request.path!!
            lateinit var response: ServerResponse
            mockedServer.find { it.url == path }.apply {
                Assert.assertNotNull(
                    "Server request not found - did you add $path to $fileName?",
                    this
                )
                response = this!!.responses[getAndIncrementRequestCount()]
            }
            return MockResponse().setResponseCode(response.responseCode).setBody(response.responseBody).apply {
                if (response.delay != 0) {
                    setBodyDelay(response.delay.toLong(), TimeUnit.SECONDS)
                    setHeadersDelay(response.delay.toLong(), TimeUnit.SECONDS)
                }
            }
        }
    }

    private fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java.getResource("/assets/$jsonFileName")
        org.junit.Assert.assertNotNull(
            "Have you added the local resource correctly?, Hint: name it as: $jsonFileName and put in /assets",
            inputStream
        )
        return inputStream?.readText() ?: ""
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
