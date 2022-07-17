package com.lyc.gitassistant.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lyc.gitassistant.common.utils.notNull
import com.lyc.gitassistant.ui.ext.getVmClazz
import com.lyc.gitassistant.ui.ext.inflateBindingWithGeneric

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity(){

    lateinit var mViewModel: VM
    lateinit var mViewBind: VB

    fun layoutId(): Int = 0

    /**
     * viewBinding 和 viewModel创建之后调用
     */
    abstract fun initView(savedInstanceState: Bundle?)

    fun showLoading(message: String = "正在努力加载..."){}

    fun dismissLoading(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBind().notNull({
            setContentView(it)
        }, {
            setContentView(layoutId())
        })
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()

    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 创建LiveData数据观察者
     */
    fun createObserver(){}

    open fun initViewBind(): View? {
        mViewBind = inflateBindingWithGeneric(layoutInflater)
        return mViewBind.root
    }

}