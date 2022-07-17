package com.lyc.gitassistant

import com.lyc.gitassistant.common.imageloader.YCImageLoaderManager
import com.lyc.gitassistant.common.imageloader.glideImpl.YCGlideImageLoader
import com.lyc.gitassistant.ui.base.BaseApp
import com.lyc.gitassistant.viewmodel.AppViewModel
import com.tencent.mmkv.MMKV
import leakcanary.LeakCanary

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */


//Application全局的ViewModel，里面存放了一些账户信息
val appViewModel: AppViewModel by lazy { GitAssistantApp.appViewModelInstance }

class GitAssistantApp : BaseApp() {

    companion object {
        lateinit var instance: GitAssistantApp
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)

        //init third sdk or something must init before application started.
        initNecessarySdks()
    }

    private fun initNecessarySdks() {
        // LeakCanary初始化
        ///初始化图片加载 使用glide
        YCImageLoaderManager.initialize(YCGlideImageLoader(this))
        //mmkv初始化
        MMKV.initialize(this,this.filesDir.absolutePath + "/mmkv")

    }

}