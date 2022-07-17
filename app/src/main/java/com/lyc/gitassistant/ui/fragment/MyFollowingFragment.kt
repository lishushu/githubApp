package com.lyc.gitassistant.ui.fragment

import android.os.Bundle
import com.lyc.gitassistant.databinding.FragmentMyFollowingBinding
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.viewmodel.FragMyFollowViewModel


class MyFollowingFragment:BaseFragment<FragMyFollowViewModel, FragmentMyFollowingBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        //初始化
        mViewBind.followToolbar.toolbar.run {
            init("首页")
        }
    }

}