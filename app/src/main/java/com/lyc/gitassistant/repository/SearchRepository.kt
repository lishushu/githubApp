package com.lyc.gitassistant.repository

import android.content.Context
import com.lyc.gitassistant.GitAssistantApp
import com.lyc.gitassistant.common.utils.FlatConversionInterface
import com.lyc.gitassistant.common.utils.FlatMapResponse2ResponseResult
import com.lyc.gitassistant.entity.ReposConversion
import com.lyc.gitassistant.entity.response.GitRepositoryEntity
import com.lyc.gitassistant.entity.response.RepoSearchResult
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.base.ResultCallBack
import com.lyc.gitassistant.network.observer.ResultProgressObserver
import com.lyc.gitassistant.network.service.SearchService

class SearchRepository {

    /**
     * 搜索
     * @param q 关键字
     * @param sort 搜索排序依据，比如best_match
     * @param order 排序
     */
    fun searchRepos(context: Context, q: String, sort: String, order: String, page: Int,
                    resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val searchOb = ApiServiceFactory.getApiInstance(SearchService::class.java)
            .searchRepos(query = q, page = page, sort = sort, order = order)
            .flatMap {
            FlatMapResponse2ResponseResult(it, object :
                FlatConversionInterface<RepoSearchResult<GitRepositoryEntity>> {
                override fun onConversion(t: RepoSearchResult<GitRepositoryEntity>?): ArrayList<Any> {
                    val list = arrayListOf<Any>()
                    t?.items?.apply {
                        this.forEach { data ->
                            val item =
                                ReposConversion.reposToReposUIModel(GitAssistantApp.instance, data)
                            list.add(item)
                        }
                    }
                    return list
                }
            })
        }
        ApiServiceFactory.executeResult(searchOb,
            object : ResultProgressObserver<ArrayList<Any>>(context, page == 1) {
                override fun onPageInfo(first: Int, current: Int, last: Int) {
                    super.onPageInfo(first, current, last)
                    resultCallBack?.onPage(first, current, last)
                }

                override fun onSuccess(result: ArrayList<Any>?) {
                    resultCallBack?.onSuccess(result)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                    resultCallBack?.onFailure()
                }
            })

    }

}