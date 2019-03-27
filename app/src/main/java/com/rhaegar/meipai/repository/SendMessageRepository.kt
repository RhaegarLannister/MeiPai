package com.rhaegar.meipai.repository

import android.bluetooth.BluetoothAdapter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/27
 */
@Singleton
class SendMessageRepository @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter){


    fun sendRailLeftMove(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["RailLeftMove"]=b
    }

    fun sendRailRightMove(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["RailRightMove"]=b
    }

    fun sendRailConfirm(b: Boolean,action:Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["RailConfirm"]=b
        hashMap["RailABPoint"]=action
    }

    fun sendYawLeftMove(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["YawLeftMove"]=b
    }

    fun sendYawRightMove(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["YawRightMove"]=b
    }

    fun sendYawConfirm(b: Boolean,action:Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["YawConfirm"]=b
        hashMap["YawABPoint"]=action
    }

    fun sendPointCancel(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["PointCancel"]=b
    }

    fun sendPointPhoto(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["PointPhoto"]=b
    }

    fun sendRailSpeed(speed: Int) {
        val hashMap = HashMap<String, Any>()
        hashMap["RailSpeed"]=speed
    }

    fun sendYawSpeed(speed: Int) {
        val hashMap = HashMap<String, Any>()
        hashMap["YawSpeed"]=speed
    }

    fun sendPhotoInterval(speed: Float) {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoInterval"]=speed
    }

    fun sendVideoCtrl(b: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["VideoCtrl"]=b
    }

    fun sendPhotoNumber(speed: Float) {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoNumber"]=speed
    }

    fun sendPhotoShutter(speed: Float) {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoShutter"]=speed
    }

    fun sendPhotoCtrl(speed: Boolean) {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoShutter"]=speed
    }

}