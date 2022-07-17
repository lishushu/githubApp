package com.lyc.gitassistant.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.BuildConfig
import com.lyc.gitassistant.entity.response.AccessToken
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.ResultState
import com.lyc.gitassistant.network.service.LoginService
import com.lyc.gitassistant.repository.LoginRepository
import com.lyc.gitassistant.repository.request
import com.lyc.gitassistant.ui.base.BaseViewModel

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
class FragOauthViewModel: BaseViewModel() {

    private val loginRepository by lazy {
        LoginRepository()
    }

    /**
     * 登录结果
     */
    val loginResult = MutableLiveData<Boolean>()


    /**
     * 登录执行
     */
    fun oauth(context: Context, code: String) {
        loginRepository.oauth(context, code, loginResult)
    }

}