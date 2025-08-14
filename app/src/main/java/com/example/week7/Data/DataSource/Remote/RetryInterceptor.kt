package com.example.week7.Data.DataSource.Remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val initialDelayMs: Long = 1000
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var retryCount = 0
        var lastException: IOException? = null

        while (retryCount < maxRetries) {
            try {
                if (retryCount > 0) {
                    Thread.sleep(initialDelayMs * retryCount)
                }

                response = chain.proceed(request)
                if (response.isSuccessful || !shouldRetry(response.code)) {
                    return response
                }

            } catch (e: IOException) {
                lastException = e
            }
            retryCount++
        }

        throw lastException ?: IOException("Max retries ($maxRetries) reached")
    }

    private fun shouldRetry(statusCode: Int): Boolean {
        return statusCode in 500..599 || statusCode == 429
    }
}