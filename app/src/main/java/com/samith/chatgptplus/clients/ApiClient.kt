package com.samith.chatgptplus.clients

import android.content.Context
import com.samith.chatgptplus.utils.Constants
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Provides an interface for communicating with the OpenAI API to generate responses based on user prompts.
 *
 * @property mediaType The media type used for requests to the API.
 * @property client The OkHttpClient used to make requests to the API.
 * @property requestTime The time it took to receive a response from the API.
 * @property readTimeout The read timeout for requests to the API.
 * @property writeTimeout The write timeout for requests to the API.
 * @property connectTimeout The connect timeout for requests to the API.
 * @property maxTokens The maximum number of tokens to generate in a response.
 * @property temperature The "temperature" of the model used to generate responses.
 * @property apiKey The API key used to authenticate requests to the API.
 * @property apiUrl The URL for the API endpoint used to generate responses.
 * @property model The name of the model used to generate responses.
 * @property prompt The prompt provided by the user for which a response is generated.
 * @property isMaxTokensEnabled Whether or not the maximum number of tokens setting is enabled.
 */
class OpenAIClient {
    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    private val client: OkHttpClient
    private var requestTime = 0.0
    private var readTimeout: Long = 0
    private var writeTimeout: Long = 0
    private var connectTimeout: Long = 0
    private var maxTokens = 0.0
    private var temperature = 0.0
    private var apiKey: String? = null
    private var apiUrl: String? = null
    private var model: String? = null
    private var prompt: String? = null
    private var isMaxTokensEnabled = false


    /**
     * Initializes the OkHttpClient with default settings and a read timeout of 25 seconds.
     */
    init {
        client = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val start = System.nanoTime()
                val request = chain.request()
                val response = chain.proceed(request)
                val end = System.nanoTime()
                val duration = end - start
                val millis = duration / 1e6
                requestTime = millis
                response
            })
            .build()
        setTimeout(25000)
    }


    fun setReadTimeout(readTimeout: Long) {
        this.readTimeout = readTimeout
    }

    fun setWriteTimeout(writeTimeout: Long) {
        this.writeTimeout = writeTimeout
    }

    fun setConnectTimeout(connectTimeout: Long) {
        this.connectTimeout = connectTimeout
    }


    fun setTimeout(timeout: Long) {
        setReadTimeout(timeout)
        setWriteTimeout(timeout)
        setConnectTimeout(timeout)
    }

    fun setApiUrl(apiUrl: String?) {
        this.apiUrl = apiUrl
    }

    fun setApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    fun setModel(model: String?) {
        this.model = model
    }

    fun getModel(): String? {
        return model
    }

    fun setPrompt(prompt: String?) {
        this.prompt = prompt
    }

    fun getPrompt(): String? {
        return prompt
    }

    fun setMaxTokens(maxTokens: Double) {
        this.maxTokens = maxTokens
    }

    fun setMaxTokensEnabled(isMaxTokensEnabled: Boolean) {
        this.isMaxTokensEnabled = isMaxTokensEnabled
    }

    fun setTemperature(temperature: Double) {
        this.temperature = temperature
    }

    @Throws(JSONException::class)
    fun getSettings(): JSONObject {
        val json = JSONObject()
            val jsonArray = JSONArray()
            val jsonMessageObj = JSONObject()
            jsonMessageObj.put("role", "user")
            jsonMessageObj.put("content", prompt)
            jsonArray.put(jsonMessageObj)
            json.put("model", model)
            json.put("messages", jsonArray)
            json.put("temperature", temperature)
            if (isMaxTokensEnabled) json.put("max_tokens", maxTokens)

        return json
    }

    @Throws(JSONException::class)
    fun generateResponse(callback: Callback) {
        val json = getSettings()
        val body = json.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(apiUrl!!)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(body)
            .build()
        client.newCall(request).enqueue(callback)
    }


    @Throws(JSONException::class)
    fun getResponse(responseBody: String?): String? {
        val jsonObject = JSONObject(responseBody)
        var resultText: String? = null

            val jsonArray = jsonObject.getJSONArray("choices")
            val jsonMessageObj = jsonArray.getJSONObject(0).getJSONObject("message")
            resultText = jsonMessageObj.getString("content")
            resultText.trim { it <= ' ' }

        return resultText
    }

    companion object {
        const val GPT_3_5_TURBO = Constants.MODEL
    }
}
