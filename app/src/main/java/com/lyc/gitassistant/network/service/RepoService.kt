package com.lyc.gitassistant.network.service

import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.entity.response.GitRepositoryEntity
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface RepoService {

    @GET("users/{user}/starred")
    fun getStarredRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("sort") sort: String = "updated",
            @Query("per_page") per_page: Int = ConfigConstant.PAGE_SIZE
    ): Observable<Response<ArrayList<GitRepositoryEntity>>>

    @GET("user/repos")
    fun getUserRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query("page") page: Int,
            @Query("type") type: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String,
            @Query("per_page") per_page: Int = ConfigConstant.PAGE_SIZE
    ): Observable<Response<ArrayList<GitRepositoryEntity>>>

}
