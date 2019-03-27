package com.rhaegar.meipai.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.rhaegar.meipai.R
import com.rhaegar.meipai.util.TranslucentUtils

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
abstract class BaseActivity: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExtActivityManager.getInstance().push(this)
        if (isFullScreen()){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }else{
            initTopBarSetting()
        }
    }


    override fun onDestroy() {
        ExtActivityManager.getInstance().remove(this)
        super.onDestroy()
    }



    /**
     * 供不同界面顶部导航栏与状态栏样式设置重载
     *
     * @return [DrawableRes]
     */
    @DrawableRes
    protected fun getTopNavBarDrawableResId(): Int {
        return R.drawable.gradient_c16_bg
    }

    /**
     * 初始化顶部栏设置
     */
    private fun initTopBarSetting() {
        // 设置透明状态栏
        val topNavBarDrawableResId = getTopNavBarDrawableResId()
        TranslucentUtils.setStatusBarBackgroundResource(this, topNavBarDrawableResId, true)

    }

    open fun isFullScreen(): Boolean{
        return false
    }


    /**
     * 获取状态栏高度
     */
    protected fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

}