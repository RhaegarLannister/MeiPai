package com.rhaegar.meipai.repository

import android.app.Application
import android.content.Intent
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/27
 */
@Singleton
class SendMessageRepository @Inject constructor(val app: Application){

    val gson=Gson()

    fun sendRailLeftMove(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["RailLeftMove"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendRailRightMove(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["RailRightMove"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendRailConfirm(b: Boolean,action:Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["RailConfirm"]=b
        hashMap["RailABPoint"]=action
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendYawLeftMove(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["YawLeftMove"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendYawRightMove(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["YawRightMove"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendYawConfirm(b: Boolean,action:Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["YawConfirm"]=b
        hashMap["YawABPoint"]=action
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPointCancel(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["PointCancel"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPointPhoto(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["PointPhoto"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendRailSpeed(speed: Int): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["RailSpeed"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendYawSpeed(speed: Int): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["YawSpeed"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoInterval(speed: Float): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoInterval"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendVideoCtrl(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["VideoCtrl"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoNumber(speed: Int): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoNumber"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoShutter(speed: Float): String? {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoShutter"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoCtrl(speed: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoCtrl"]=speed
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendArriveSame(it: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["ArriveSame"]=it
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendVideoToA(it: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["VideoToA"]=it
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendVideoToB(it: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["VideoToB"]=it
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendVideoTakePhoto(b: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["VideoTakePhoto"]=b
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoToA(it: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoToA"]=it
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

    fun sendPhotoToB(it: Boolean):String {
        val hashMap = HashMap<String, Any>()
        hashMap["PhotoToB"]=it
        val json = gson.toJson(hashMap)
        val intent = Intent()
        intent.action=BlueToothRepository.ACTION_SEND_MESSAGE
        intent.putExtra(BlueToothRepository.EXTRA_DATA,json)
        app.sendBroadcast(intent)
        return json
    }

}