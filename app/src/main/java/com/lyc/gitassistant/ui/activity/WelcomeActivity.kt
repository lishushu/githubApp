package com.lyc.gitassistant.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.databinding.ActivityWelcomeBinding
import com.lyc.gitassistant.ui.base.BaseActivity
import com.lyc.gitassistant.ui.base.BaseViewModel

class WelcomeActivity : BaseActivity<BaseViewModel, ActivityWelcomeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val delay = if (!AppCacheUtil.isFirst()) {
            1500L
        } else 300L
        enterToNextPage(delay)
    }

    private fun enterToNextPage(delay: Long) {
        Log.w("welcome", "goNext===> mViewBinding:" + mViewBind.welcomeImv)
        mViewBind.welcomeImv.postDelayed({
            AppCacheUtil.setFirst(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, delay.coerceAtLeast(0))
    }

}