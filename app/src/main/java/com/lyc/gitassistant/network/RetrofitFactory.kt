package com.lyc.gitassistant.network

import androidx.viewbinding.BuildConfig
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.common.utils.LogUtils
import com.lyc.gitassistant.network.base.ResultObserver
import com.lyc.gitassistant.network.bizinterceptor.PageInfoInterceptor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 */
class RetrofitFactory private constructor() {

    private var accessTokenStorage: String? = ""
        get() = AppCacheUtil.getAccessToken()

    val retrofit: Retrofit

    init {
        //打印请求log
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(ConfigConstant.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor())
                .addInterceptor(PageInfoInterceptor())
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(ConfigConstant.GITHUB_API_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
    }

    /**
     * 拦截头部增加token
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            //add access token
            val accessToken = getAuthorization()

            LogUtils.debugInfo("headerInterceptor", accessToken)

            if (!accessToken.isEmpty()) {
                LogUtils.debugInfo(accessToken)
                val url = request.url().toString()
                request = request.newBuilder()
                        .addHeader("Authorization", accessToken)
                        .url(url)
                        .build()
            }

            chain.proceed(request)
        }

    }

    /**
     * 获取token
     */
    fun getAuthorization(): String {
        return "token $accessTokenStorage"

    }

    companion object {

        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null)
                            mRetrofitFactory = RetrofitFactory()
                    }

                }
                return mRetrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
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
}
