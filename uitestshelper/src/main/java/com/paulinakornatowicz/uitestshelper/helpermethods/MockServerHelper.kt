package com.paulinakornatowicz.uitestshelper.helpermethods

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertNotNull

object MockServerHelper {
    private fun getFileNameAndPath(path: String): String {
        println("------ PATH: $path")
        return "${
            path.removePrefix("/").replace("/", "_")
                .replace(":", "_").replace("&", "_")
                .replace("?", "_")
        }.json"
    }

    fun dispatch(
        testName: String,
        request: RecordedRequest
    ): MockResponse {
        val fileName = getFileNameAndPath(request.path!!)
        return if (fileName.isNotEmpty()) MockResponse().setResponseCode(
            readResponseFromFile(fileName.replace(".json", "_responseCode.json"), testName).toInt()
        ).setBody(readResponseFromFile(fileName, testName))
        else MockResponse().setResponseCode(404).setBody("{}")
    }

    private fun readResponseFromFile(jsonFileName: String, testName: String): String {
        val inputStream = this::class.java.getResource("/assets/$testName/$jsonFileName")
        println("---- readResponseFromFile inputStream: $inputStream")
        assertNotNull(
            "Have you added the local resource correctly?, Hint: name it as: $jsonFileName and put in /assets/$testName",
            inputStream
        )
        return inputStream?.readText() ?: ""
    }
}