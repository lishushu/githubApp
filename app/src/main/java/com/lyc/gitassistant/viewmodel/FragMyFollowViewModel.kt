package com.lyc.gitassistant.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lyc.gitassistant.appViewModel
import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.network.base.ResultCallBack
import com.lyc.gitassistant.repository.ReposRepository
import com.lyc.gitassistant.ui.base.BaseViewModel
import com.lyc.gitassistant.viewmodel.paging.IPagingLoad

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
class FragMyFollowViewModel: BaseViewModel(), IPagingLoad,ResultCallBack<ArrayList<Any>> {

    val reposRepository: ReposRepository by lazy { ReposRepository() }

    val dataList = MutableLiveData<ArrayList<Any>>()

    val loadState = MutableLiveData<LoadState>()

    var page = 1
    private val pageSize = ConfigConstant.PAGE_SIZE

    override fun getCount(): Int {
        return dataList.value?.size?:0
    }

    private fun loadData(context: Context) {
        loadState.value = LoadState.Loading
        reposRepository.getUserStarRepos(appViewModel.userInfo.value?.login?:"",page,this)
    }
    override fun refresh(context: Context) {
        page = 1
        loadData(context)
    }
    override fun loadMore(context: Context) {
        loadData(context)
    }
    override fun hasMore(curPageCount: Int): Boolean {
        return curPageCount >= pageSize
    }

    override fun isFirstPage(): Boolean {
        return page == 1
    }

    override fun onFailure() {
        super.onFailure()
        loadState.value = LoadState.LoadError
    }
    override fun onSuccess(result: ArrayList<Any>?) {
        //更新数据 通知view数据刷新
        result?.apply {
            dataList.value = result
        }
        result?.let {
            if(it.size > 0) {
                page++
            }
        }
    }

}