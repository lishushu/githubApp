package com.lyc.gitassistant.entity.request

import com.google.gson.annotations.SerializedName
import com.lyc.gitassistant.BuildConfig
import java.util.*

/**
 * 请求登录的参数
 */
class LoginRequestParams {

    var scopes: List<String>? = null
        private set
    var note: String? = null
        private set
    @SerializedName("client_id")
    var clientId: String? = null
        private set
    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        fun generate(): LoginRequestParams {
            val model = LoginRequestParams()
            model.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            model.note = BuildConfig.APPLICATION_ID
            return model
        }
    }
}
