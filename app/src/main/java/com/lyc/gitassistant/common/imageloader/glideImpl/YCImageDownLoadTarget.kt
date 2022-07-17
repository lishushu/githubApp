package com.lyc.gitassistant.common.imageloader.glideImpl

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lyc.gitassistant.common.imageloader.YCImageLoader

import java.io.File

/**
 * Glide 图片下载对象
 */
class YCImageDownLoadTarget constructor(private val mCallback: YCImageLoader.Callback?) : SimpleTarget<File>() {

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        mCallback?.onSuccess(resource)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        mCallback?.onStart()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        mCallback?.onFail(null)
    }

}