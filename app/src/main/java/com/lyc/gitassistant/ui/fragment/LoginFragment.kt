package com.lyc.gitassistant.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.lyc.gitassistant.R
import com.lyc.gitassistant.databinding.FragmentLoginBinding
import com.lyc.gitassistant.ui.base.BaseFragment
import com.lyc.gitassistant.ui.ext.showMessage
import com.lyc.gitassistant.ui.navigation.nav
import com.lyc.gitassistant.ui.navigation.navigateAction
import com.lyc.gitassistant.viewmodel.FragLoginViewModel
import com.tbruyelle.rxpermissions2.RxPermissions


class LoginFragment:BaseFragment<FragLoginViewModel, FragmentLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.loginSub.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
            .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted: Boolean ->
                if (granted) { // Always true pre-M
                    // I can control the camera now
                    nav().navigateAction(R.id.action_loginFragment_to_loginOauthFragment)
                } else {
                    // Oups permission denied
                    showTipDialog()
                }
            }
    }

    private fun showTipDialog() {
        showMessage("授权提示",
            "需要授权GitAssistant访问您的存储空间以便更好的缓存数据.",
            "去授权",
            {
                val localIntent = Intent()
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", activity?.packageName, null);
                startActivity(localIntent)
            }, "取消", {}

        )
    }

}