package com.lyc.gitassistant.ui.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lyc.gitassistant.R
import com.lyc.gitassistant.entity.uiEntity.ReposUIEntity
import com.lyc.gitassistant.ui.ext.CommonUtils

class GitRepoAdapter(data: MutableList<ReposUIEntity>?)
    : BaseDelegateMultiAdapter<ReposUIEntity, BaseViewHolder>(data) {

    private val GitRepo = 1

    init {
        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ReposUIEntity>() {
            override fun getItemType(data: List<ReposUIEntity>, position: Int): Int {
                return GitRepo
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(GitRepo, R.layout.layout_git_repo_item)
        }
    }

    fun clearData() {
        data.clear()
    }

    fun getCount(): Int {
        return data.size
    }

    fun addAll(dataNew: ArrayList<Any>) {
       dataNew.forEach {
           if(it is ReposUIEntity) {
               data.add(it)
           }
       }
    }

    override fun convert(helper: BaseViewHolder, item: ReposUIEntity) {
        when (helper.itemViewType) {
            GitRepo -> {
                item.run {
                    helper.setText(
                        R.id.item_home_author,
                        ownerName.ifEmpty { "--" }
                    )
                    helper.setText(R.id.item_lang_txv, repositoryType)
                    helper.setText(R.id.item_home_content, repositoryDes)
                    helper.setText(R.id.item_star_num, "${repositoryStar}·stars")
                    CommonUtils.loadUserHeaderImage(helper.getView<ImageView>(R.id.author_profile_imv), ownerPic)
                }
            }
        }
    }

}


