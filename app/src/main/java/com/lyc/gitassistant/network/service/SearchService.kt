package com.lyc.gitassistant.network.service

import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.entity.response.GitRepositoryEntity
import com.lyc.gitassistant.entity.response.RepoSearchResult
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {

    @GET("search/repositories")
    fun searchRepos(
        @Query(value = "q", encoded = true) query: String,
        @Query("sort") sort: String = "best%20match",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = ConfigConstant.PAGE_SIZE
    ): Observable<Response<RepoSearchResult<GitRepositoryEntity>>>


}
