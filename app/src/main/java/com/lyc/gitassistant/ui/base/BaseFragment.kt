package com.lyc.gitassistant.ui.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.lyc.gitassistant.ui.ext.dismissLoadingExt
import com.lyc.gitassistant.ui.ext.getVmClazz
import com.lyc.gitassistant.ui.ext.inflateBindingWithGeneric
import com.lyc.gitassistant.ui.ext.showLoadingExt

/**
 * @description
 * @author jokerlee
 * @date 2022/7/16
 */
abstract class BaseFragment<VM:BaseViewModel, VB: ViewBinding> : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    //该类绑定的 ViewBinding
    private var _binding: VB? = null
    val mViewBind: VB get() = _binding!!

    /**
     * 当前Fragment绑定的视图布局
     */
    fun layoutId(): Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = inflateBindingWithGeneric(inflater,container,false)
        return mViewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    open fun initView(savedInstanceState: Bundle?){}

    /**
     * 创建观察者
     */
    open fun createObserver(){}

    override fun onResume() {
        super.onResume()
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    fun showLoading(message: String = "努力加载中..."){
        showLoadingExt(message)
    }

    fun dismissLoading(){
        dismissLoadingExt()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean, inclusive: Boolean) {
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId,
            args, NavOptions.Builder().setPopUpTo(controller.graph.id, inclusive).build())
        if (finishStack) {
            activity?.finish()
        }
    }

    fun navigationBack(view: View) {
        val controller = Navigation.findNavController(view)
        controller.popBackStack()
    }

}