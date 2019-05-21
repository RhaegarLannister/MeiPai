package com.rhaegar.meipai.ui.main

import android.app.Application
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rhaegar.meipai.R
import com.rhaegar.meipai.repository.SendMessageRepository
import com.rhaegar.meipai.util.L
import com.rhaegar.meipai.util.SPUtils
import java.util.*
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class MainViewModel @Inject constructor(
    val app: Application,
    private val sendMessageRepository: SendMessageRepository
) :
    ViewModel() {


    private val index = MutableLiveData<Int>()//当前选择页面

    val title: LiveData<String> = Transformations.switchMap(index) {
        val data = MutableLiveData<String>()
        data.value = getTitleByIndex(it)
        return@switchMap data
    }//当前选择页面标题

    val startPositionValue = MutableLiveData<Int>()//起始位置设置状态 1未设置 2调整A 3确定A 4调整B 5确定B 6再次调整A
    val rotatePositionValue = MutableLiveData<Int>()//旋转位置设置状态 1 2 3 4 5 6

    val startPositionClickAble: LiveData<Boolean> = Transformations.switchMap(rotatePositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = /*it == 1 || it == 5*/true
        data
    }//滑轨起点是否可以设置

    val startPositionSureClickAble: LiveData<Boolean> = Transformations.switchMap(startPositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 2 || it == 4 || it == 6
        data
    }//滑轨起点确定是否可以点击

    val rotatePositionClickAble: LiveData<Boolean> = Transformations.switchMap(startPositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = /*it == 1 || it == 5*/true
        data
    }//旋转起点是否可以设置

    val rotatePositionSureClickAble: LiveData<Boolean> = Transformations.switchMap(rotatePositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 2 || it == 4 || it == 6
        data
    }//旋转起点确定是否可以点击

    val isClickCancel = MutableLiveData<Boolean>()

    val startPositionText: LiveData<String> = Transformations.switchMap(startPositionValue) {
        val liveData = MutableLiveData<String>()
        when (it) {
            1 -> {
                liveData.value = app.getString(R.string.start_position_setting_1)
            }
            2, 6 -> {
                liveData.value = app.getString(R.string.start_position_setting_a)
            }
            3, 4 -> {
                liveData.value = app.getString(R.string.start_position_setting_b)
            }
            5 -> {
                liveData.value = app.getString(R.string.start_position_setting_6)
            }

        }
        liveData
    }//起始位置文字

    val rotatePositionText: LiveData<String> = Transformations.switchMap(rotatePositionValue) {
        val liveData = MutableLiveData<String>()
        when (it) {
            1 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_1)
            }
            2, 6 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_a)
            }
            3, 4 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_b)
            }
            5 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_6)
            }
        }
        liveData
    }//旋转位置文字


    val slideSpeed = MutableLiveData<Int>()//滑轨速度值
    val horizontalRotation = MutableLiveData<Int>()//水平旋转值

    val isTakeVideo = MutableLiveData<Boolean>()//是否正在拍摄
    var cb = MutableLiveData<Boolean>()//两轴是否同时到达

    val spaceTime = MutableLiveData<String>()//拍摄间隔
    val takeNumbers = MutableLiveData<String>()//拍摄张数
    val buttonTimes = MutableLiveData<String>()//按下快门时间


    val recText = MutableLiveData<String>()//按下快门时间

    val finishNumbersValue = MutableLiveData<Int>()//完成张数


    var finishNumbers: LiveData<String>



    val finishTimes: LiveData<String> = Transformations.switchMap(takeNumbers) {
        val liveData = MutableLiveData<String>()
        if (spaceTime.value!=null&&buttonTimes.value!=null){
            val s=(spaceTime.value!!.toInt()+buttonTimes.value!!.toInt())*it.toInt()
            liveData.value = app.getString(R.string.finish_time_value, s / 60, s % 60)
        }else{
            val s=0
            liveData.value = app.getString(R.string.finish_time_value, s / 60, s % 60)
        }
        liveData
    }//完成时间

    val percent: LiveData<Int>


    val isTakeDelayVideo = MutableLiveData<Boolean>()//是否正在拍摄延迟

    val videoToA= MutableLiveData<Boolean>()
    val videoToB= MutableLiveData<Boolean>()
    val photoToA= MutableLiveData<Boolean>()
    val photoToB= MutableLiveData<Boolean>()

    init {
        index.value = 0
        startPositionValue.value = 1
        rotatePositionValue.value = 1
        slideSpeed.value = SPUtils["slideSpeed", 80] as Int
        horizontalRotation.value = SPUtils["horizontalRotation", 60] as Int
        cb.value = SPUtils["cb", false] as Boolean

        isTakeVideo.value = false
        isTakeDelayVideo.value = false
        isClickCancel.value = false


        /*sendSlideSpeed()
        sendHorizontalRotation()*/
        buttonTimes.postValue(SPUtils["buttonTimes", "60"] as String)
        spaceTime.postValue(SPUtils["spaceTime", "40"] as String)
        takeNumbers.postValue(SPUtils["takeNumbers", "50"] as String)

        finishNumbers = Transformations.switchMap(finishNumbersValue) {
            val liveData = MutableLiveData<String>()
            liveData.value = app.getString(R.string.finish_numbers_value, it, takeNumbers.value?.toInt() ?: 0)
            liveData
        }//完成张数
        finishNumbersValue.value = 0
        percent = Transformations.switchMap(finishNumbersValue) {
            val liveData = MutableLiveData<Int>()

            val value = ((it / (takeNumbers.value?.toFloat() ?: it.toFloat())) * 100).toInt()
            liveData.value = value
            if (value==100){
                isTakeDelayVideo.value=false
            }
            return@switchMap liveData
        }

        recText.value = "未收到数据"

        videoToA.value=false
        videoToB.value=false
        photoToA.value=false
        photoToB.value=false
    }


    private fun getTitleByIndex(index: Int): String {

        if (index != 1) {
            isTakeVideo.value = false
            sendMessageRepository.sendVideoCtrl(false)
        }

        if (index != 2) {
            isTakeDelayVideo.value = false
            sendMessageRepository.sendPhotoCtrl(false)
        }

        return when (index) {
            0 -> {
                isClickCancel.value = false
                app.getString(R.string.start_position)
            }
            1 -> {
                app.getString(R.string.vedio)
            }
            else -> {
                app.getString(R.string.delay)
            }
        }
    }

    fun changeIndex(position: Int) {
        index.value = position
        if (position == 0) {
            startPositionValue.value = 1
            rotatePositionValue.value = 1
        }else{
            sendData()
        }
    }

    fun startOrPauseTakeVideo() {
        sendMessageRepository.sendVideoCtrl(isTakeVideo.value != true)
    }

    fun startOrPauseTakeDelayVideo() {
        sendMessageRepository.sendPhotoCtrl(isTakeDelayVideo.value != true)

    }


    fun longClick(index: Int) {
        isClickCancel.value = false
        when (index) {
            1, 2 -> {
                when (startPositionValue.value) {
                    1 -> {
                        startPositionValue.value = 2
                    }
                    2 -> {

                    }
                    3 -> {
                        startPositionValue.value = 4
                    }
                    4 -> {

                    }
                    5 -> {
                        startPositionValue.value = 6
                    }
                    6 -> {

                    }
                }

                if (index == 1) {
                    sendMessageRepository.sendRailLeftMove(true)
                } else {
                    sendMessageRepository.sendRailRightMove(true)
                }
            }
            3, 4 -> {
                when (rotatePositionValue.value) {
                    1 -> {
                        rotatePositionValue.value = 2
                    }
                    2 -> {

                    }
                    3 -> {
                        rotatePositionValue.value = 4
                    }
                    4 -> {

                    }
                    5 -> {
                        rotatePositionValue.value = 6
                    }
                    6 -> {

                    }
                }

                if (index == 3) {
                    sendMessageRepository.sendYawLeftMove(true)
                } else {
                    sendMessageRepository.sendYawRightMove(true)
                }
            }
            5, 6 -> {
                if (index == 5) {
                    sendMessageRepository.sendVideoToA(true)
                } else {
                    sendMessageRepository.sendVideoToB(true)
                }
            }
            7, 8 -> {
                if (index == 7) {
                    sendMessageRepository.sendPhotoToA(true)
                } else {
                    sendMessageRepository.sendPhotoToB(true)
                }
            }
        }
    }


    fun sure(index: Int) {
        L.e("确定$index")
        if (index == 1) {
            when (startPositionValue.value) {
                2, 6 -> {
                    startPositionValue.value = 3
                    sendMessageRepository.sendRailConfirm(true, true)
                }
                4 -> {
                    startPositionValue.value = 5
                    sendMessageRepository.sendRailConfirm(true, false)

                }
            }
        } else {
            when (rotatePositionValue.value) {
                2, 6 -> {
                    rotatePositionValue.value = 3
                    sendMessageRepository.sendYawConfirm(true, true)
                }
                4 -> {
                    rotatePositionValue.value = 5
                    sendMessageRepository.sendYawConfirm(true, false)
                }
            }
        }
    }

    fun cancel() {
        L.e("取消")
        isClickCancel.value = true
        startPositionValue.value = 1
        rotatePositionValue.value = 1
        sendMessageRepository.sendPointCancel(true)
    }

    fun takePhoto() {
        L.e("拍照")
        sendMessageRepository.sendPointPhoto(true)
    }


    fun takePhotoVideoPage() {
        L.e("拍照 视频页面")
        sendMessageRepository.sendVideoTakePhoto(true)
    }


    fun onCancel(arowId: Int) {
        when (arowId) {
            1 -> {
                sendMessageRepository.sendRailLeftMove(false)
            }
            2 -> {
                sendMessageRepository.sendRailRightMove(false)
            }
            3 -> {
                sendMessageRepository.sendYawLeftMove(false)
            }
            4 -> {
                sendMessageRepository.sendYawRightMove(false)
            }
            5 -> {
                sendMessageRepository.sendVideoToA(false)
            }
            6 -> {
                sendMessageRepository.sendVideoToB(false)
            }
            7 -> {
                sendMessageRepository.sendPhotoToA(false)
            }
            8 -> {
                sendMessageRepository.sendPhotoToB(false)
            }

        }

    }

    fun sendSlideSpeed() {
        sendMessageRepository.sendRailSpeed(slideSpeed.value!!)
    }

    fun sendHorizontalRotation() {
        sendMessageRepository.sendYawSpeed(horizontalRotation.value!!)
    }

    fun sendData() {
        var i = 0
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                when (i) {
                    0 -> {
                        sendSlideSpeed()
                    }
                    1 -> {
                        sendHorizontalRotation()
                    }
                    2 -> {
                        sendMessageRepository.sendPhotoNumber(takeNumbers.value!!.toInt())
                    }
                    3 -> {
                        sendMessageRepository.sendPhotoInterval(spaceTime.value!!.toFloat())

                    }
                    4 -> {
                        sendMessageRepository.sendPhotoShutter(buttonTimes.value!!.toFloat())
                    }
                    5->{
                        sendArriveSame(cb.value==true)
                    }
                    6 -> {
                        timer.cancel()
                    }
                }
                i++
            }
        }
        timer.schedule(timerTask, 100, 100)
    }

    fun after(s: Editable, index: Int) {
        val str = s.toString()
        when (index) {
            1 -> {
                try {
                    val toFloat = str.toFloat()
                    if (str != spaceTime.value) {
                        SPUtils.put("spaceTime", s)
                        spaceTime.value = str
                        sendMessageRepository.sendPhotoInterval(toFloat)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            2 -> {
                try {
                    val toFloat = str.toInt()
                    if (str != takeNumbers.value) {
                        SPUtils.put("takeNumbers", s)
                        takeNumbers.value = str
                        sendMessageRepository.sendPhotoNumber(toFloat)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            3 -> {
                try {
                    val toFloat = str.toFloat()
                    if (str != buttonTimes.value) {
                        SPUtils.put("buttonTimes", s)
                        buttonTimes.value = str
                        sendMessageRepository.sendPhotoShutter(toFloat)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun sendArriveSame(it: Boolean) {
        sendMessageRepository.sendArriveSame(it)
    }

    fun videoToA(b: Boolean) {
        videoToA.value=b
        if (b){
            videoToB.value=false
        }
    }

    fun videoToB(b: Boolean) {
        videoToB.value=b
        if (b){
            videoToA.value=false
        }
    }

    fun photoToA(b: Boolean) {
        photoToA.value=b
        if (b){
            photoToB.value=false
        }
    }

    fun photoToB(b: Boolean) {
        photoToB.value=b
        if (b){
            photoToA.value=false
        }
    }

}