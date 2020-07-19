package com.paulinakornatowicz.uitestshelper.helpermethods

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object MockServerHelper {
    private fun getFileNameAndPath(path: String): String {
        println("------ PATH: $path")
        return "${path.removePrefix("/").replace("/", "_")}.json"
    }

    fun dispatch(request: RecordedRequest, responseCode: Int = 200): MockResponse {
        val fileName = getFileNameAndPath(request.path!!)
        return if (fileName.isNotEmpty()) {
            MockResponse().setResponseCode(responseCode).setBody(readFile(fileName))
        } else {
            MockResponse().setResponseCode(404).setBody("{}")
        }
    }

    @Throws(IOException::class)
    fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java.getResourceAsStream("/assets/$jsonFileName")
            ?: throw NullPointerException(
                "Have you added the local resource correctly?, "
                        + "Hint: name it as: " + jsonFileName
            )
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }
}