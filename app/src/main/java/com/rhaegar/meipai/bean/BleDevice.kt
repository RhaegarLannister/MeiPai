package com.rhaegar.meipai.bean

import android.bluetooth.BluetoothDevice
import java.util.*

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
data class BleDevice(
    val device: BluetoothDevice,
    val rssi: Int,
    val scanRecord: ByteArray,
    val state: Int
) {
    override fun toString(): String {
        return "BleDevice(device=$device, rssi=$rssi, scanRecord=${Arrays.toString(scanRecord)}, state=$state)"
    }
}