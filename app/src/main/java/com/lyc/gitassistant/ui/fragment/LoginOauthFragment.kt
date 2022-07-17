package com.lyc.gitassistant.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.lyc.gitassistant.AppCacheUtil
import com.lyc.gitassistant.BuildConfig
import com.lyc.gitassistant.R
import com.lyc.gitassistant.common.ConfigConstant.GITHUB_OAUTH_CALLBACK
import com.lyc.gitassistant.databinding.FragmentLoginOauthBinding
import com.lyc.gitassistant.repository.parseState
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.init
import com.lyc.gitassistant.ui.navigation.nav
import com.lyc.gitassistant.ui.navigation.navigateAction
import com.lyc.gitassistant.viewmodel.FragOauthViewModel
import org.jetbrains.anko.toast

class LoginOauthFragment:BaseFragment<FragOauthViewModel, FragmentLoginOauthBinding>() {

    private val oauthWebview: WebView by lazy {
        mViewBind.oauthWebview
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.oauthToolbar.toolbar.init("授权登录")
        initWeb()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.run {
            loginResult.observe(viewLifecycleOwner, Observer { result ->
                //根据结果返回，跳转主页
                if (result != null && result == true) {
                    nav().navigateAction(R.id.action_loginOauthFragment_to_homeFragment)
                } else {
                    activity?.toast("登录失败")
                    nav().navigateUp()
                }
            })
        }
    }

    private fun initWeb() {
        val settings = oauthWebview.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.domStorageEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.setAppCacheEnabled(true)

        val webViewClient: WebViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                mViewBind.oauthWebviewLoadingBar.visibility = View.GONE
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (request != null && request.url != null &&
                    request.url.toString().startsWith(GITHUB_OAUTH_CALLBACK)) {
                    val code = request.url.getQueryParameter("code")
                    if (code != null) {
                        context?.let {
                            mViewModel.oauth(it,code)
                        }
                    }
                    return true
                }
                return false
            }
        }
        oauthWebview.webViewClient = webViewClient
        val url = "https://github.com/login/oauth/authorize?" +
                "client_id=${BuildConfig.CLIENT_ID}&" +
                "state=app&redirect_uri=$GITHUB_OAUTH_CALLBACK";
        oauthWebview.loadUrl(url)
    }

}