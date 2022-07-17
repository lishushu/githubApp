package com.lyc.gitassistant.ui.ext

import android.graphics.Point
import android.widget.ImageView
import com.lyc.gitassistant.R
import com.lyc.gitassistant.common.imageloader.YCImageLoaderManager
import com.lyc.gitassistant.common.imageloader.YCLoadOption
import me.hgj.jetpackmvvm.demo.app.util.dp
import java.text.SimpleDateFormat
import java.util.*


/**
 * 通用工具类
 */
object CommonUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT


    /**
     * 加载用户头像
     */
    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(40.dp, 40.dp)) {
        val option = YCLoadOption()
                .setDefaultImg(R.drawable.default_usr_logo_bg)
                .setErrorImg(R.drawable.default_usr_logo_bg)
                .setCircle(true)
                .setSize(size)
                .setUri(url)
        YCImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }


    fun getDateStr(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0, 10)
    }

}