package com.gkgio.data

import com.gkgio.data.exception.ErrorJsonTransformer
import com.gkgio.data.ext.getErrorJsonIfPossible
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HostInterceptor @Inject constructor(
    private val errorJsonTransformer: ErrorJsonTransformer
) : Interceptor {

    companion object {
        const val CONNECT_URL = "80.249.146.240"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .host(CONNECT_URL)
            .build()

        val response = chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
        response.body.getErrorJsonIfPossible()?.let {
            throw errorJsonTransformer.transform(it)
        }

        return response
    }
}