package com.lyc.gitassistant.common.imageloader

import kotlin.properties.Delegates

/**
 * 图片加载管理
 */
class YCImageLoaderManager private constructor(private var mImageLoader: YCImageLoader) : YCImageLoader by mImageLoader {

    companion object {
        //委托notNull，这个值在被获取之前没有被分配，它就会抛出一个异常。
        var sInstance: YCImageLoaderManager by Delegates.notNull()

        /**
         * 静态初始化、建议Application中初始化
         * @param imageLoader
         */
        fun initialize(imageLoader: YCImageLoader) {
            sInstance = YCImageLoaderManager(imageLoader)
        }
    }

    /**
     * 图片加载对象
     */
    fun imageLoader(): YCImageLoader {
        return this
    }

    /**
     * 强制转换的图片加载对象
     */
    fun <T : YCImageLoader> imageLoaderExtend(): T {
        return this as T
    }
}