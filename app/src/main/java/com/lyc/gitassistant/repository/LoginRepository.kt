package com.lyc.gitassistant.repository

import android.content.Context
import android.text.TextUtils
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.lifecycle.MutableLiveData
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.BuildConfig
import com.lyc.gitassistant.entity.response.AccessToken
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.observer.ResultProgressObserver
import com.lyc.gitassistant.network.service.LoginService

/**
 * 登录数据仓库对象
 */
class LoginRepository {

    private var accessTokenStorage: String? = ""
        get() = AppCacheUtil.getAccessToken()

    /**
     * 登录
     */
    fun oauth(context: Context, code: String, loginRes: MutableLiveData<Boolean>) {

        clearTokenStorage()
        val loginService = ApiServiceFactory.getApiInstance(LoginService::class.java)

        val tokenOb = loginService.authorizationsCode(BuildConfig.CLIENT_ID,BuildConfig.CLIENT_SECRET,code)
        ApiServiceFactory.executeResult(tokenOb, object : ResultProgressObserver<AccessToken>(context) {
            override fun onSuccess(result: AccessToken?) {
                if(TextUtils.isEmpty(result?.access_token)) {
                    loginRes.value = false
                } else {
                    result?.access_token?.let { AppCacheUtil.setAccessToken(it) }
                    loginRes.value = true
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
