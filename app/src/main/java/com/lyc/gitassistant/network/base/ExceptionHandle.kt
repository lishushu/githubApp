package com.lyc.gitassistant.network.base

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

object ExceptionHandle {

    fun handleException(e: Throwable?): NetException {
        val ex: NetException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = NetException(Error.NETWORK_ERROR,e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = NetException(Error.PARSE_ERROR,e)
                    return ex
                }
                is ConnectException -> {
                    ex = NetException(Error.NETWORK_ERROR,e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = NetException(Error.SSL_ERROR,e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = NetException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = NetException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = NetException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is NetException -> return it

                else -> {
                    ex = NetException(Error.UNKNOWN,e)
                    return ex
                }
            }
        }
        ex = NetException(Error.UNKNOWN,e)
        return ex
    }
}