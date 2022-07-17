package com.lyc.gitassistant.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lyc.gitassistant.common.ConfigConstant
import com.lyc.gitassistant.common.utils.dp2px
import com.lyc.gitassistant.databinding.FragmentSearchBinding
import com.lyc.gitassistant.ui.adapter.GitRepoAdapter
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.ui.ext.initFloatBtn
import com.lyc.gitassistant.ui.ext.initFooter
import com.lyc.gitassistant.ui.widgets.SpaceItemDecoration
import com.lyc.gitassistant.viewmodel.FragSearchViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView

class SearchFragment:BaseFragment<FragSearchViewModel, FragmentSearchBinding>() {

    //适配器
    private val reposAdapter: GitRepoAdapter by lazy { GitRepoAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.searchToolbar.toolbar.init("搜索仓库")
        mViewBind.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    if(it.isEmpty()) {
                        reposAdapter.clearData()
                        reposAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
        mViewBind.searchEditText.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(p0: View?, keyCode: Int, p2: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val searchQ = mViewBind.searchEditText.text.toString()
                    if (searchQ.isNullOrBlank()) {
                        return false
                    }
                    doSearch()
                    return true
                }
                return false
            }
        })

        mViewBind.searchIconBtn.setOnClickListener {
            mViewBind.searchEditText.text?.let {
                doSearch()
            }
        }

        //初始化recyclerView
        getRecyclerView().init(LinearLayoutManager(context), reposAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, context!!.dp2px(8)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                mViewModel.loadMoreData(context!!)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(mViewBind.searchComList.floatBtn)
        }

        mViewBind.searchComList.swipeRefresh.isEnabled = false
        val linearLayoutManager = LinearLayoutManager(activity!!)
        getRecyclerView().layoutManager = linearLayoutManager
        getRecyclerView().adapter = reposAdapter
    }

    private fun getRecyclerView(): SwipeRecyclerView {
        return mViewBind.searchComList.recyclerView
    }

    private fun doSearch() {
        context?.let {
            val searchKey = mViewBind.searchEditText.text.toString()
            if(searchKey.isEmpty()) return
            mViewModel.doSearch(it, searchKey)
        }
    }

    override fun createObserver() {
        mViewModel.dataList.observe(this, Observer { items ->
            items?.apply {
                mViewBind.searchComList.swipeRefresh.isRefreshing = false
                if (items.size > 0) {
                    if (mViewModel.isFirstData()) {
                        reposAdapter.clearData()
                    }
                    reposAdapter.addAll(items)
                    reposAdapter.notifyDataSetChanged()
                } else {
                    if (mViewModel.isFirstData()) {
                        reposAdapter.clearData()
                        reposAdapter.notifyDataSetChanged()
                    }
                }
                mViewBind.searchComList.recyclerView.loadMoreFinish(items.isEmpty(), items.size >= ConfigConstant.PAGE_SIZE)
            }
        })
    }

}