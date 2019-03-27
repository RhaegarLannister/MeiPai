package com.rhaegar.meipai.ui.connect

import android.Manifest
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.base.BaseActivity
import com.rhaegar.meipai.databinding.ActivityConnectBinding
import com.rhaegar.meipai.ui.main.MainActivity
import com.rhaegar.meipai.util.L
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:连接蓝牙
 * Date: 2019/3/25
 */
class ConnectActivity : BaseActivity() {


    private lateinit var activityConnectBinding: ActivityConnectBinding

    @Inject
    lateinit var app: Application

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: ConnectViewModel by lazy {
        ViewModelProviders.of(this, factory).get(ConnectViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN), 111)
        activityConnectBinding = DataBindingUtil.setContentView(this, R.layout.activity_connect)
        activityConnectBinding.viewModel = mViewModel
        activityConnectBinding.lifecycleOwner=this
        mViewModel.scanBleList.observe(this, Observer {
            startActivity(Intent(this,MainActivity::class.java))
        })

    }

    override fun isFullScreen(): Boolean {
        return true
    }

}