package com.lyc.gitassistant.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.lyc.gitassistant.ui.base.BaseViewModel

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userInfo = MutableLiveData<UserInfoEntity>()
    init {
//        userInfo.value = AppCacheUtil.isFirst()
    }
    fun updateUserInfo(info:UserInfoEntity) {
        userInfo.value = info
    }

}