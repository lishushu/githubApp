package com.lyc.gitassistant.common.utils

import android.util.Log

const val DEFAULT_LOG_TAG = "GitAssistant"

/**
 *
 * 根据true|false 控制日志输出
 */
var enableLog = true

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = DEFAULT_LOG_TAG) =
    log(LEVEL.V, tag, this)
fun String.logd(tag: String = DEFAULT_LOG_TAG) =
    log(LEVEL.D, tag, this)
fun String.logi(tag: String = DEFAULT_LOG_TAG) =
    log(LEVEL.I, tag, this)
fun String.logw(tag: String = DEFAULT_LOG_TAG) =
    log(LEVEL.W, tag, this)
fun String.loge(tag: String = DEFAULT_LOG_TAG) =
    log(LEVEL.E, tag, this)

private fun log(level: LEVEL, tag: String, message: String) {
    if (!enableLog) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}