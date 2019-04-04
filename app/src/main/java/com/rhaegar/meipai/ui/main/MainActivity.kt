package com.rhaegar.meipai.ui.main

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.databinding.ActivityMainBinding
import com.rhaegar.meipai.databinding.ItemMainTabBinding
import com.rhaegar.meipai.repository.BlueToothRepository
import com.rhaegar.meipai.util.L
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.item_main_tab.view.*
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mAdapter: MainTabAdapter by lazy { MainTabAdapter(supportFragmentManager) }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var mBluetoothLeService: BlueToothRepository? = null

    var isConnect=true

    val timer=Timer()

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter(BlueToothRepository.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(BlueToothRepository.ACTION_DATA_AVAILABLE)
        intentFilter.addAction(BlueToothRepository.ACTION_GATT_DISCONNECTED)
        registerReceiver(mGattUpdateReceiver,intentFilter )

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewModel = mViewModel
        activityMainBinding.adapter = mAdapter
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.vpContent.offscreenPageLimit=3
        activityMainBinding.tabLayout.setupWithViewPager(activityMainBinding.vpContent)
        activityMainBinding.tabLayout.post {
            for (i in 0..2) {
                val inflate = LayoutInflater.from(this).inflate(
                    R.layout.item_main_tab,
                    null
                )
                inflate.iv.setImageResource(mAdapter.icons[i])
                val itemMainTabBinding = DataBindingUtil.bind<ItemMainTabBinding>(inflate)
                itemMainTabBinding?.icon = mAdapter.icons[i]
                activityMainBinding.tabLayout.getTabAt(i)?.customView=inflate
            }
        }



        val observer= Observer<Int> {

            if (((mViewModel.startPositionValue.value==5||mViewModel.startPositionValue.value==6)&&(mViewModel.rotatePositionValue.value==1||mViewModel.rotatePositionValue.value==5||mViewModel.rotatePositionValue.value==6))||
                ((mViewModel.rotatePositionValue.value==5||mViewModel.rotatePositionValue.value==6)&&(mViewModel.startPositionValue.value==1||mViewModel.startPositionValue.value==5||mViewModel.startPositionValue.value==6))
                    ){
                (activityMainBinding.tabLayout.getTabAt(1)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener false
                }

                (activityMainBinding.tabLayout.getTabAt(2)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener false
                }
            }else{
                (activityMainBinding.tabLayout.getTabAt(1)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener mViewModel.isClickCancel.value==false
                }

                (activityMainBinding.tabLayout.getTabAt(2)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener mViewModel.isClickCancel.value==false
                }
            }
        }

        mViewModel.startPositionValue.observe(this,observer)
        mViewModel.rotatePositionValue.observe(this,observer)

        bindService(Intent(this, BlueToothRepository::class.java),mServiceConnection, Context.BIND_AUTO_CREATE)

        val task=object : TimerTask() {
            override fun run() {
                if (!isConnect){
                    search()
                }
            }

        }
        timer.schedule(task,0,3000)
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
            mViewModel.sendData()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }


    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if( BlueToothRepository.ACTION_GATT_SERVICES_DISCOVERED == action) {
                isConnect=true
                mViewModel.sendData()
            }else if (BlueToothRepository.ACTION_DATA_AVAILABLE==action){
                val s = intent.getStringExtra(BlueToothRepository.EXTRA_DATA)
                try {
                    L.e(s)
                    mViewModel.recText.value=s
                    if (s.contains("PhotoFinishNum")) {
                        val jsonObject = JSONObject(s)
                        val int = jsonObject.getInt("PhotoFinishNum")
                        mViewModel.finishNumbersValue.value=int
                        if (int==mViewModel.takeNumbers.value?.toInt()){
                            mViewModel.cancelTimer()
                        }
                    }else if (s.contains("PhotoCtrl")){
                        val jsonObject = JSONObject(s)
                        val b = jsonObject.getBoolean("PhotoCtrl")
                        mViewModel.isTakeDelayVideo.value = b
                        mViewModel.startTime = System.currentTimeMillis()
                        if (b){
                            mViewModel.startTimer()
                        }else{
                            mViewModel.cancelTimer()
                        }
                    }else if (s.contains("VideoCtrl")){
                        val jsonObject = JSONObject(s)
                        val b = jsonObject.getBoolean("VideoCtrl")
                        mViewModel.isTakeVideo.value = b

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }else if (BlueToothRepository.ACTION_GATT_DISCONNECTED==action){
                isConnect=false
                search()
            }
        }
    }



    fun search(){
        if (mBluetoothLeService?.bluetoothAdapter?.isEnabled==false){
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent,888)
        }else{
            mBluetoothLeService?.search()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==888&&resultCode== Activity.RESULT_OK){
            mBluetoothLeService?.search()
        }
    }



    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
