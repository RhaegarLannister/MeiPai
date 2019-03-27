package com.rhaegar.meipai.repository

import android.app.Application
import android.bluetooth.*
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.rhaegar.meipai.bean.BleDevice
import com.rhaegar.meipai.util.L
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/19
 */
@Singleton
class BlueToothRepository @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val app: Application
) {

    private val scanBleList = MutableLiveData<MutableList<BleDevice>>()
    private var mBluetoothGatt: BluetoothGatt? = null
    private val UUID_NOTIFY = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    private val UUID_SERVICE = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    var mNotifyCharacteristic: BluetoothGattCharacteristic? = null
    init {
        scanBleList.value = mutableListOf()
    }

    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        val bleDevice = BleDevice(device, rssi, scanRecord, 0)
        if (!TextUtils.isEmpty(device.name)) {
            L.e(device.name + bleDevice.toString())
        }
        if (device.address=="C8:0F:10:A7:75:9C"){
            connect(device.address)
            stopScan()
        }
        scanBleList.value?.add(bleDevice)
    }

    fun stopScan(){
        bluetoothAdapter.stopLeScan(mLeScanCallback)
    }

    fun search(): MutableLiveData<MutableList<BleDevice>> {

        bluetoothAdapter.startLeScan(mLeScanCallback)
        return scanBleList
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


    fun findService(gattServices: List<BluetoothGattService>) {
        for (gattService in gattServices) {
            if (gattService.uuid.toString().equals(UUID_SERVICE.toString(), ignoreCase = true)) {
                val gattCharacteristics = gattService.characteristics
                for (gattCharacteristic in gattCharacteristics) {
                    if (gattCharacteristic.uuid.toString().equals(UUID_NOTIFY.toString(), ignoreCase = true)) {
                        mNotifyCharacteristic = gattCharacteristic
                        return
                    }
                }
            }
        }
    }




    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            L.e("onConnectionStateChange")
            if (status == BluetoothGatt.GATT_SUCCESS) {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    L.e("onConnectionStateChange1111")
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    L.e("onConnectionStateChange2222")
                    mBluetoothGatt?.close()
                    mBluetoothGatt = null
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            L.e("onServicesDiscovered")

            if (status == BluetoothGatt.GATT_SUCCESS) {
                L.e("onServicesDiscovered1111")
                findService(gatt.services)
            } else {
                L.e("onServicesDiscovered2222")
                if (mBluetoothGatt?.device?.uuids == null){

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

            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            L.e("onCharacteristicChanged")

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
}