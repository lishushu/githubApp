package com.lyc.gitassistant.ui.fragment

import android.os.Bundle
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.R
import com.lyc.gitassistant.databinding.FragmentMyFollowingBinding
import com.lyc.gitassistant.databinding.FragmentMyProfileBinding
import com.lyc.gitassistant.databinding.FragmentSearchBinding
import com.lyc.gitassistant.network.NetworkApi
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.ui.ext.showMessage
import com.lyc.gitassistant.ui.navigation.nav
import com.lyc.gitassistant.ui.navigation.navigateAction
import com.lyc.gitassistant.viewmodel.FragProfileViewModel
import kotlin.math.log


class ProfileFragment:BaseFragment<FragProfileViewModel, FragmentMyProfileBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mViewBind.logouBtn.setOnClickListener {
            showMessage("确需要退出登录吗？","提示", "退出",{
                logoOut()
            },"取消",{

            })
        }
    }

    private fun logoOut() {
        //清空cookie
        NetworkApi.INSTANCE.cookieJar.clear()
        AppCacheUtil.setAccessToken("")
        nav().navigateAction(R.id.action_homeFragment_to_loginFragment)
    }

    override fun createObserver() {
        super.createObserver()
    }

}