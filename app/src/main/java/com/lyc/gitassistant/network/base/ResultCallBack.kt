package com.lyc.gitassistant.network.base

/**
 * 结果回调
 */
interface ResultCallBack<T> {

    fun onPage(first: Int, current: Int, last: Int) {

    }

    fun onSuccess(result: T?)

    fun onCacheSuccess(result: T?) {

    }

    fun onFailure() {

    }

}