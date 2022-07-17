package com.lyc.gitassistant.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.common.utils.dp2px
import com.lyc.gitassistant.databinding.FragmentMyFollowingBinding
import com.lyc.gitassistant.ui.adapter.GitRepoAdapter
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.ui.ext.initFloatBtn
import com.lyc.gitassistant.ui.ext.initFooter
import com.lyc.gitassistant.ui.widgets.SpaceItemDecoration
import com.lyc.gitassistant.viewmodel.FragMyFollowViewModel
import com.lyc.gitassistant.viewmodel.LoadState
import com.yanzhenjie.recyclerview.SwipeRecyclerView


class MyFollowingFragment:BaseFragment<FragMyFollowViewModel, FragmentMyFollowingBinding>() {

    //适配器
    private val reposAdapter: GitRepoAdapter by lazy { GitRepoAdapter(arrayListOf()) }

    private val swpRcv : SwipeRecyclerView by lazy {
        mViewBind.followingList.recyclerView
    }
    private val swpRefreshLayout : SwipeRefreshLayout by lazy {
        mViewBind.followingList.swipeRefresh
    }

    override fun initView(savedInstanceState: Bundle?) {
        //初始化
        mViewBind.followToolbar.toolbar.run {
            init("我的星标")
        }
        //初始化recyclerView
        swpRcv.init(LinearLayoutManager(context), reposAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, context!!.dp2px(8)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                mViewModel.loadMore(context!!)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(mViewBind.followingList.floatBtn)
        }
        val linearLayoutManager = LinearLayoutManager(activity!!)
        swpRcv.layoutManager = linearLayoutManager
        swpRcv.adapter = reposAdapter

        //初次加载数据
        swpRefreshLayout.init {
            mViewModel.refresh(context!!)
        }
        swpRefreshLayout.isRefreshing = true
        mViewModel.refresh(context!!)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.dataList.observe(this, Observer { items ->
            items?.apply {
                swpRefreshLayout.isRefreshing = false
                if (items.size > 0) {
                    if (mViewModel.isFirstPage()) {
                        reposAdapter.clearData()
                    }
                    reposAdapter.addAll(items)
                    reposAdapter.notifyDataSetChanged()
                } else {
                    if (mViewModel.isFirstPage()) {
                        reposAdapter.clearData()
                        reposAdapter.notifyDataSetChanged()
                    }
                }
                swpRcv.loadMoreFinish(items.isEmpty(), mViewModel.hasMore(this.size))
            }
        })
        mViewModel.loadState.observe(this, Observer {
            when(it) {
                LoadState.Loading -> {}
                LoadState.LoadError -> {
                    swpRefreshLayout.isRefreshing = false
                }
                else -> {}
            }
        })
    }

}