package com.lyc.gitassistant

import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.tencent.mmkv.MMKV

object AppCacheUtil {

    const val appCacheInfo = "AppCacheData"
    private const val keyForAccessToken = "ACCESS_TOKEN_KEY"
    private const val keyForStart = "Start_Record_KEY"
    private const val keyForUser = "USER_INFO_KEY"

    /**
     * 是否是第一次登陆
     */
    fun isFirst(): Boolean {
        val kv = MMKV.mmkvWithID(appCacheInfo)
        return kv.decodeBool(keyForStart, true)
    }
    /**
     * 是否是第一次登陆
     */
    fun setFirst(first:Boolean): Boolean {
        val kv = MMKV.mmkvWithID(appCacheInfo)
        return kv.encode(keyForStart, first)
    }

    fun getAccessToken(): String? {
        val kv = MMKV.mmkvWithID(appCacheInfo)
        return kv.decodeString(keyForAccessToken, "")
    }
    fun setAccessToken(accessToken:String): Boolean {
        val kv = MMKV.mmkvWithID(appCacheInfo)
        return kv.encode(keyForAccessToken, accessToken)
    }
}