package com.lyc.gitassistant.repository

import com.lyc.gitassistant.GitAssistantApp
import com.lyc.gitassistant.common.utils.FlatConversionInterface
import com.lyc.gitassistant.common.utils.FlatMapResponse2ResponseResult
import com.lyc.gitassistant.entity.ReposConversion
import com.lyc.gitassistant.entity.response.GitRepositoryEntity
import com.lyc.gitassistant.network.ApiServiceFactory
import com.lyc.gitassistant.network.base.ResultCallBack
import com.lyc.gitassistant.network.observer.ResultTipObserver
import com.lyc.gitassistant.network.service.RepoService
import io.reactivex.Observable
import retrofit2.Response

/**
 * 仓库相关数据获取
 */
class ReposRepository  {

    /**
     * 获取用户star的仓库数据
     */
    fun getUserStarRepos(userName: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val netService = ApiServiceFactory.getApiInstance(RepoService::class.java)
            .getStarredRepos(true, userName, page)
            .doOnNext {
                //缓存到本地数据库
            }
        reposListRequest(netService, resultCallBack, page)
    }

    private fun reposListRequest(observer: Observable<Response<ArrayList<GitRepositoryEntity>>>, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int) {
        val service = observer.flatMap {
            FlatMapResponse2ResponseResult(it, object :
                FlatConversionInterface<ArrayList<GitRepositoryEntity>> {
                override fun onConversion(t: ArrayList<GitRepositoryEntity>?): ArrayList<Any> {
                    val list = arrayListOf<Any>()
                    t?.onEach { data ->
                        val item = ReposConversion.reposToReposUIModel(GitAssistantApp.instance, data)
                        list.add(item)
                    }
                    return list
                }
            })
        }
        ApiServiceFactory.executeResult(service, object : ResultTipObserver<ArrayList<Any>>(GitAssistantApp.instance) {
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