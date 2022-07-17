package com.lyc.gitassistant.network.bizinterceptor

import com.google.gson.Gson
import com.lyc.gitassistant.network.base.ApiResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class TokenOutInterceptor : Interceptor {

    val gson: Gson by lazy { Gson() }
    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body() != null && response.body()!!.contentType() != null) {
            val mediaType = response.body()!!.contentType()
            val string = response.body()!!.string()
            val responseBody = ResponseBody.create(mediaType, string)
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }
    }
}