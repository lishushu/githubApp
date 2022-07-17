package com.lyc.gitassistant.network.observer

import android.accounts.NetworkErrorException
import android.content.Context
import com.lyc.gitassistant.network.base.ResultObserver
import org.jetbrains.anko.toast
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


/**
 * 带错误提示的网络请求返回处理
 */
abstract class ResultTipObserver<T>(private val context: Context) : ResultObserver<T>() {

    override fun onInnerCodeError(code: Int, message: String) {
        super.onInnerCodeError(code, message)
        codeError(code, message)
    }

    override fun onError(e: Throwable) {
        super.onError(e)

        if (isNumeric(e.message)) {
            codeError(e.message!!.toInt(), e.cause?.message ?: "")
            return
        }
        try {
            if (e is ConnectException
                    || e is TimeoutException
                    || e is NetworkErrorException
                    || e is UnknownHostException) {
                context.toast("网络异常")
            } else {
                context.toast(e.message ?: "未知错误")
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }


    private fun codeError(code: Int, message: String) {
        context.toast("$code : $message")
    }

    private fun isNumeric(str: String?): Boolean {
        if (str == null) {
            return false
        }
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }
}
