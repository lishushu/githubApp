package com.lyc.gitassistant.ui.fragment

import android.os.Bundle
import com.lyc.gitassistant.R
import com.lyc.gitassistant.databinding.FragmentHomeBinding
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.ui.ext.initMain
import com.lyc.gitassistant.ui.ext.interceptLongClick
import com.lyc.gitassistant.viewmodel.FragHomeViewModel

/**
 * APP的主页fragment容器 包含viewpager + 底部的导航bar
 */
class HomeFragment : BaseFragment<FragHomeViewModel, FragmentHomeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        mViewBind.mainViewpager.initMain(this)
        //初始化 bottomBar
        mViewBind.mainBottom.init {
            when (it) {
                R.id.menu_main -> mViewBind.mainViewpager.setCurrentItem(0, false)
                R.id.menu_search_project -> mViewBind.mainViewpager.setCurrentItem(1, false)
                R.id.menu_me -> mViewBind.mainViewpager.setCurrentItem(2, false)
            }
        }
        mViewBind.mainBottom.interceptLongClick(R.id.menu_main,R.id.menu_search_project,R.id.menu_me)
    }

}