package com.lyc.gitassistant.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lyc.gitassistant.R
import com.lyc.gitassistant.entity.response.UserInfoEntity
import com.lyc.gitassistant.ui.ext.CommonUtils

class FollowingAdapter(data: MutableList<UserInfoEntity>?)
    : BaseDelegateMultiAdapter<UserInfoEntity, BaseViewHolder>(data) {

    private val FollowingType = 1

    init {
        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<UserInfoEntity>() {
            override fun getItemType(data: List<UserInfoEntity>, position: Int): Int {
                return FollowingType
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(FollowingType, R.layout.layout_following_item)
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
           if(it is UserInfoEntity) {
               data.add(it)
           }
       }
    }

    override fun convert(helper: BaseViewHolder, item: UserInfoEntity) {
        when (helper.itemViewType) {
            FollowingType -> {
                item.run {
                    helper.setText(
                        R.id.item_home_author,
                        name?.ifEmpty { "--" }
                    )
                    helper.setText(R.id.item_time_txv, "")
                    helper.setText(R.id.item_following_content, "")
                    val imv = helper.getView<ImageView>(R.id.author_profile_imv)
                    imv.setImageDrawable(null)
                    avatarUrl?.let {
                        CommonUtils.loadUserHeaderImage(imv,
                            it
                        )
                    }
                }
            }
        }
    }

}


