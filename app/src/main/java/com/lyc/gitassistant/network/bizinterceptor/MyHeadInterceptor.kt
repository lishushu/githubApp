package com.lyc.gitassistant.network.bizinterceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        //模拟
//        builder.addHeader("token", "xxxxx").build()
        builder.addHeader("device", "Android").build()
        return chain.proceed(builder.build())
    }

}