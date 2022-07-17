package com.lyc.gitassistant.repository

import android.content.Context
import android.text.TextUtils
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.lifecycle.MutableLiveData
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.BuildConfig
import com.lyc.gitassistant.appViewModel
import com.lyc.gitassistant.common.utils.FlatMapResponse2Result
import com.lyc.gitassistant.common.utils.FlatMapResult2Response
import com.lyc.gitassistant.common.utils.LogUtils
import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.observer.ResultProgressObserver
import com.lyc.gitassistant.network.service.LoginService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function

/**
 * 登录数据仓库对象
 */
class LoginRepository {

    private var accessTokenStorage: String? = ""
        get() = AppCacheUtil.getAccessToken()

    private val userRepository by lazy { UserRepository() }
    /**
     * 登录
     */
    fun oauth(context: Context, code: String, loginRes: MutableLiveData<Boolean>) {

        clearTokenStorage()
        val loginService = ApiServiceFactory.getApiInstance(LoginService::class.java)
        val tokenService = loginService
            .authorizationsCode(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code)
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                val token = it.access_token
                token!!
            }.doOnNext {
                LogUtils.debugInfo("token $it")
                accessTokenStorage = it
                AppCacheUtil.setAccessToken(it)
            }.onErrorResumeNext(Function<Throwable, Observable<String>> { t ->
                LogUtils.debugInfo("token onErrorResumeNext ")
                clearTokenStorage()
                Observable.error(t)
            })

        val userService = userRepository.getPersonInfoObservable()

        val authorizations = Observable.zip(tokenService, userService,
            BiFunction<String, UserInfoEntity, UserInfoEntity> { _, user ->
                user
            }).flatMap {
            FlatMapResult2Response(it)
        }

        ApiServiceFactory.executeResult(authorizations, object : ResultProgressObserver<UserInfoEntity>(context) {
            override fun onSuccess(result: UserInfoEntity?) {
                if(TextUtils.isEmpty(accessTokenStorage)) {
                    loginRes.value = false
                } else {
                    accessTokenStorage?.let { AppCacheUtil.setAccessToken(it) }
                    loginRes.value = true
                    //更新 & 保存用户信息
                    result?.let { appViewModel.updateUserInfo(info = it) }
                }
            }

            override fun onCodeError(code: Int, message: String) {
                loginRes.value = false
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                loginRes.value = false
            }

        })

    }

    /**
     * 清除token
     */
    fun clearTokenStorage() {
        accessTokenStorage = ""
    }

    /**
     * 退出登录
     */
    fun logout(context: Context) {
        accessTokenStorage = ""
        clearCookies()
    }

    fun clearCookies() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeAllCookies(null)
        WebStorage.getInstance().deleteAllData()
        CookieManager.getInstance().flush()
    }
}
