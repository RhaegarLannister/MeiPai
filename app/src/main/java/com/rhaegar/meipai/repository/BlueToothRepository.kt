package com.rhaegar.meipai.repository

import android.bluetooth.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.text.TextUtils
import com.rhaegar.meipai.App.Companion.app
import com.rhaegar.meipai.bean.BleDevice
import com.rhaegar.meipai.util.L
import dagger.android.DaggerService
import org.json.JSONObject
import java.util.*
import javax.inject.Inject


/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/19
 */
class BlueToothRepository : DaggerService() {


    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    private var mBluetoothGatt: BluetoothGatt? = null
    companion object {
        val ACTION_GATT_CONNECTED = "com.rhaegar.meipai.ACTION_GATT_CONNECTED"
        val ACTION_GATT_DISCONNECTED = "com.rhaegar.meipai.ACTION_GATT_DISCONNECTED"
        val ACTION_GATT_SERVICES_DISCOVERED = "com.rhaegar.meipai.ACTION_GATT_SERVICES_DISCOVERED"
        val ACTION_DATA_AVAILABLE = "com.rhaegar.meipai.ACTION_DATA_AVAILABLE"
        val EXTRA_DATA = "com.rhaegar.meipai.EXTRA_DATA"
        val ACTION_SEND_MESSAGE="ACTION_SEND_MESSAGE"
    }

    private val UUID_NOTIFY = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    private val UUID_SERVICE = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    var mNotifyCharacteristic: BluetoothGattCharacteristic? = null



    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        val bleDevice = BleDevice(device, rssi, scanRecord, 0)
        if (!TextUtils.isEmpty(device.name)) {
            L.e(device.name + bleDevice.toString())
        }
        connect(device.address)
        stopScan()
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(mGattUpdateReceiver, IntentFilter(ACTION_SEND_MESSAGE))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mGattUpdateReceiver)
    }

    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BlueToothRepository.ACTION_SEND_MESSAGE==action){
                val s = intent.getStringExtra(EXTRA_DATA)
                writeValue(s)
            }
        }
    }

    private fun stopScan() {
        bluetoothAdapter.stopLeScan(mLeScanCallback)
    }

    fun search() {
        bluetoothAdapter.startLeScan(arrayOf(UUID_SERVICE), mLeScanCallback)
    }

    fun connect(address: String?): Boolean {
        if (address == null) {
            return false
        }
        /*
        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }
*/
        val device = bluetoothAdapter.getRemoteDevice(address)

        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        if (mBluetoothGatt != null) {
            mBluetoothGatt?.close()
            mBluetoothGatt = null
        }
        mBluetoothGatt = device.connectGatt(app, false, mGattCallback)
        //mBluetoothGatt.connect();

        return true
    }


    fun writeValue(strValue: String) {
        L.e(strValue)
        if (mNotifyCharacteristic != null) {
            mNotifyCharacteristic?.value = strValue.toByteArray()
            mBluetoothGatt?.writeCharacteristic(mNotifyCharacteristic)
        }
    }

    fun findService(gattServices: List<BluetoothGattService>) {
        for (gattService in gattServices) {
            L.e(gattService.uuid.toString())

            if (gattService.uuid.toString().equals(UUID_SERVICE.toString(), ignoreCase = true)) {
                val gattCharacteristics = gattService.characteristics
                for (gattCharacteristic in gattCharacteristics) {
                    L.e(gattCharacteristic.uuid.toString())
                    if (gattCharacteristic.uuid.toString().equals(UUID_NOTIFY.toString(), ignoreCase = true)) {
                        mNotifyCharacteristic = gattCharacteristic
                        mBluetoothGatt?.setCharacteristicNotification(mNotifyCharacteristic, true)
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED)
                        return
                    }
                }
            }
        }
    }


    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            L.e("onConnectionStateChange")
            L.e(Thread.currentThread().name)
            gatt.discoverServices()
            if (status == BluetoothGatt.GATT_SUCCESS) {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    L.e("onConnectionStateChange1111")
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    L.e("onConnectionStateChange2222")
                    mBluetoothGatt?.close()
                    mBluetoothGatt = null
                    broadcastUpdate(ACTION_GATT_DISCONNECTED)
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            L.e("onServicesDiscovered")
            L.e(Thread.currentThread().name)

            if (status == BluetoothGatt.GATT_SUCCESS) {
                L.e("onServicesDiscovered1111")
                findService(gatt.services)
            } else {
                L.e("onServicesDiscovered2222")
                if (mBluetoothGatt?.device?.uuids == null) {

                }
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            L.e("onCharacteristicRead")

            if (status == BluetoothGatt.GATT_SUCCESS) {
                onReadData(characteristic)
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            L.e("onCharacteristicChanged")
            onReadData(characteristic)
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            L.e("onCharacteristicWrite")

        }

        override fun onDescriptorRead(
            gatt: BluetoothGatt,
            bd: BluetoothGattDescriptor,
            status: Int
        ) {
            L.e("onDescriptorRead")

        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            bd: BluetoothGattDescriptor,
            status: Int
        ) {
            L.e("onDescriptorWrite")

        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt, a: Int, b: Int) {
            L.e("onReadRemoteRssi")

        }

        override fun onReliableWriteCompleted(gatt: BluetoothGatt, a: Int) {
            L.e("onReliableWriteCompleted")
        }

    }

    private fun onReadData(characteristic: BluetoothGattCharacteristic) {
        val str = String(characteristic.value)
        broadcastUpdate(ACTION_DATA_AVAILABLE,str)
        L.e(str)
        try {
            if (str.contains("PhotoFinishNum")) {
                val jsonObject = JSONObject(str)
                val int = jsonObject.getInt("PhotoFinishNum")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun broadcastUpdate(action: String) {
        val intent = Intent(action)
        sendBroadcast(intent)
    }

    private fun broadcastUpdate(
        action: String,
        strValue: String
    ) {
        val intent = Intent(action)

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        // For all other profiles, writes the data formatted in HEX.

        intent.putExtra(BlueToothRepository.EXTRA_DATA, strValue)
        sendBroadcast(intent)
    }



    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal val service: BlueToothRepository
            get() = this@BlueToothRepository
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }
}