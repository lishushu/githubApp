package com.lyc.gitassistant.network

import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.network.base.ResultObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * @description
 * @author jokerlee
 * @date 2022/7/17
 */
object ApiServiceFactory {

    fun <T> getApiInstance(serviceClass: Class<T>, baseUrl: String = ConfigConstant.GITHUB_API_BASE_URL): T {
        return RetrofitFactory.createService(serviceClass)
    }

    /**
     * 执行请求返回结果
     */
    fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
    }

}