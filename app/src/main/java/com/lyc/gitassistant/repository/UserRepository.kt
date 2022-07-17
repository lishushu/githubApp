package com.lyc.gitassistant.repository
import com.lyc.gitassistant.common.utils.FlatMapResponse2Result
import com.lyc.gitassistant.common.utils.LogUtils
import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.service.UserService
import io.reactivex.Observable
import retrofit2.Response
import io.reactivex.functions.Function

class UserRepository {

    /**
     * 获取用户详细信息
     */
    fun getPersonInfoObservable(userName: String? = null): Observable<UserInfoEntity> {
        val isLoginUser = userName == null
        //根据是否有用户名，获取第三方用户数据或者当前用户数据
        val userService = if (isLoginUser) {
            ApiServiceFactory.getApiInstance(UserService::class.java).getPersonInfo(true)
        } else {
            ApiServiceFactory.getApiInstance(UserService::class.java).getUser(true, userName!!)
        }
        return doUserInfoFlat(userService, isLoginUser)
    }

    private fun doUserInfoFlat(service: Observable<Response<UserInfoEntity>>, isLoginUser: Boolean): Observable<UserInfoEntity> {
        return service.flatMap {
            FlatMapResponse2Result(it)
        }.doOnNext {
            ///如果是登录用户，保存一份数据到 SharedPreferences
            if (isLoginUser) {
                // json cahce AppCache GsonUtils.toJsonString(it)
            }
        }.onErrorResumeNext(Function<Throwable, Observable<UserInfoEntity>> { t ->
            ///拦截错误
            //userInfoStorage = ""
            LogUtils.debugInfo("userInfo onErrorResumeNext ")
            Observable.error(t)
        })
    }


}