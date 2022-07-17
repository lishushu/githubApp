package com.lyc.gitassistant.network.service

import com.lyc.gitassistant.entity.response.UserInfoEntity
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface UserService {

    @GET("user")
    fun getPersonInfo(
        @Header("forceNetWork") forceNetWork: Boolean
    ): Observable<Response<UserInfoEntity>>

}
