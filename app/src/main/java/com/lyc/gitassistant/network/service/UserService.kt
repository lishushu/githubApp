package com.lyc.gitassistant.network.service

import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.entity.response.UserInfoEntity
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface UserService {

    /**
     * 获取用户信息
     */
    @GET("user")
    fun getPersonInfo(
        @Header("forceNetWork") forceNetWork: Boolean
    ): Observable<Response<UserInfoEntity>>

    @GET("users/{user}")
    fun getUser(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String
    ): Observable<Response<UserInfoEntity>>

    @GET("users/{user}/following")
    fun getFollowing(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = ConfigConstant.PAGE_SIZE
    ): Observable<Response<ArrayList<UserInfoEntity>>>

}
