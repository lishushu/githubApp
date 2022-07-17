package com.lyc.gitassistant.viewmodel

/**
 * 上下拉加载状态
 */
enum class LoadState {
    Refresh,
    LoadMore,
    RefreshDone,
    LoadMoreDone,
    NONE
}