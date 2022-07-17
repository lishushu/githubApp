package com.lyc.gitassistant.network.service

import com.lyc.gitassistant.entity.response.AccessToken
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LoginService {

    @GET("https://github.com/login/oauth/access_token")
    @Headers("Accept: application/json")
    fun authorizationsCode(@Query("client_id") client_id: String,
                           @Query("client_secret") client_secret: String,
                           @Query("code") code: String): Observable<Response<AccessToken>>

}
