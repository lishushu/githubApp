package com.lyc.gitassistant.repository

import com.lyc.gitassistant.common.utils.FlatMapResponse2Result
import com.lyc.gitassistant.common.utils.GsonUtils
import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.service.UserService
import io.reactivex.Observable
import retrofit2.Response


class UserRepository {


    /**
     * 获取用户详细信息
     */
//    fun getPersonInfoObservable(userName: String? = null): Observable<UserInfoEntity> {
//        val isLoginUser = userName == null
//        //根据是否有用户名，获取第三方用户数据或者当前用户数据
//        val userService = if (isLoginUser) {
//            ApiServiceFactory.getApiInstance(UserService::class.java).getPersonInfo(true)
//        } else {
//            ApiServiceFactory.getApiInstance(UserService::class.java).getUser(true)
//        }
//        return doUserInfoFlat(userService, isLoginUser)
//    }


}