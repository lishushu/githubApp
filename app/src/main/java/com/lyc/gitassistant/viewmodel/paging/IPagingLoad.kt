package com.lyc.gitassistant.viewmodel.paging

import android.content.Context

/**
 * @description
 * @author jokerlee
 * @date 2022/7/17
 */
interface IPagingLoad {

    fun refresh(context: Context)

    fun loadMore(context: Context)

    fun getCount(): Int

    fun hasMore(curPageCount:Int): Boolean

    fun isFirstPage(): Boolean

}