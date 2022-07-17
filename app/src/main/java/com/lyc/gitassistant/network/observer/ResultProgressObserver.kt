package com.lyc.gitassistant.network.observer

import android.content.Context
import com.lyc.gitassistant.ui.widgets.LoadingDialog

abstract class ResultProgressObserver<T>(private val context: Context, private val needLoading: Boolean = true) : ResultTipObserver<T>(context) {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needLoading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) "努力加载中..." else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
    }
}