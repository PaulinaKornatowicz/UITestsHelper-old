package com.paulinakornatowicz.uitestshelper.credentials

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

/**
 * Reads credentials data from the file specified or default credentials.json
 *
 * @throws CredentialsParsingException if there's a problem with parsing class
 */
class CredentialsManager<T>(clazz: Class<T>, private val fileName: String = "credentials.json") {

    private val gson = Gson()

    private var accountData: T? = null

    private val context = InstrumentationRegistry.getInstrumentation().context

    init {
        try {
            val reader = gson.newJsonReader(InputStreamReader(context.assets.open(fileName)))
            accountData = gson.fromJson<T>(reader, TypeToken.get(clazz).type)
            reader.close()
        } catch (exception: Exception) {
            throw CredentialsParsingException("Error parsing credentials for class: $clazz")
        }
    }

    fun getCredentials(): T? = accountData

    class CredentialsParsingException(msg: String) : Exception(msg)
}