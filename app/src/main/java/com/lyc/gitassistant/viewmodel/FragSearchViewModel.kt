package com.lyc.gitassistant.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lyc.gitassistant.GitAssistantApp
import com.lyc.gitassistant.network.base.ResultCallBack
import com.lyc.gitassistant.repository.SearchRepository
import com.lyc.gitassistant.ui.base.BaseViewModel
import org.jetbrains.anko.runOnUiThread

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
class FragSearchViewModel: BaseViewModel(), ResultCallBack<ArrayList<Any>> {

    companion object {
        const val REPOSITORY = 0//仓库
        const val USER = 1//人
    }

    private val searchRepository: SearchRepository by lazy {
        SearchRepository()
    }

    val dataList = MutableLiveData<ArrayList<Any>>()


    var lastPage: Int = -1

    var page = 1

    /**
     * 搜索类型
     */
    var type = 0
    /**
     * 排列依据
     */
    var sort = "best%20match"
    /**
     * 排列顺序
     */
    var order = "desc"
    /**
     * 排列语言
     */
    var language = ""
    var keywords = ""

    init {
        dataList.value = arrayListOf()
    }

    fun doSearch(context: Context, keywords: String) {
        this.keywords = keywords
        refresh(context)
    }

    fun loadMoreData(context: Context) {
        loadData(context, keywords)
    }


    fun refresh(context: Context) {
        page = 1
        loadData(context, keywords)
    }

    private fun loadData(context: Context, keywords: String) {
        var searchQ = keywords
        if (searchQ.isNullOrBlank()) {
            return
        }
        if (type == REPOSITORY) {
            if (language.isNotBlank()) {
                searchQ = "$searchQ+language:$language"
            }
            searchRepository.searchRepos(context, searchQ!!, sort, order, page, this)
        } else if (type == USER) {

        }
    }

    open fun isFirstData(): Boolean = page == 1

    override fun onFailure() {

    }

    override fun onPage(first: Int, current: Int, last: Int) {
        if (last != -1) {
            lastPage = last
        }
    }

    private fun commitResult(result: ArrayList<Any>?) {
        result?.apply {
            dataList.value = result
        }
    }

    override fun onSuccess(result: ArrayList<Any>?) {
        commitResult(result)
        result?.let {
            if(it.size > 0) {
                page++
            }
        }
    }

}