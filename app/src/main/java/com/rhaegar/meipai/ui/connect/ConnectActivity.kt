package com.rhaegar.meipai.ui.connect

import android.Manifest
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.base.BaseActivity
import com.rhaegar.meipai.databinding.ActivityConnectBinding
import com.rhaegar.meipai.repository.BlueToothRepository
import com.rhaegar.meipai.ui.main.MainActivity
import java.util.*
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
    private var mBluetoothLeService: BlueToothRepository? = null
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN),
            111
        )
        activityConnectBinding = DataBindingUtil.setContentView(this, R.layout.activity_connect)
        activityConnectBinding.viewModel = mViewModel
        activityConnectBinding.lifecycleOwner = this

        registerReceiver(mGattUpdateReceiver, IntentFilter(BlueToothRepository.ACTION_GATT_SERVICES_DISCOVERED))
        bindService(Intent(this, BlueToothRepository::class.java), mServiceConnection, Context.BIND_AUTO_CREATE)

        val task = object : TimerTask() {
            override fun run() {
                    search()
            }

        }
        timer.schedule(task, 0, 3000)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mGattUpdateReceiver)
        unbindService(mServiceConnection)
        timer.cancel()
    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            mBluetoothLeService = (service as BlueToothRepository.LocalBinder).service
            search()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }

    private fun search() {
        if (mBluetoothLeService?.bluetoothAdapter?.isEnabled == false) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, 888)
        } else {
            mBluetoothLeService?.search()
        }
    }


    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BlueToothRepository.ACTION_GATT_SERVICES_DISCOVERED == action) {
                mViewModel.connectState.value = getString(R.string.connected)
                startActivity(Intent(this@ConnectActivity, MainActivity::class.java))
                finish()
            } else if (BlueToothRepository.ACTION_GATT_DISCONNECTED == action) {
                search()
            }
        }
    }


    override fun isFullScreen(): Boolean {
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 888) {
            if (resultCode == Activity.RESULT_OK) {
                mBluetoothLeService?.search()
            } else {
                finish()
            }
        }
    }

}