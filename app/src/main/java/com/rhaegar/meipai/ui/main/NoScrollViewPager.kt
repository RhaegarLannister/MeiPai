package com.rhaegar.meipai.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager



/**
 * Author: Li Hai Kun
 * Description:不可滑动的ViewPager
 * Date: 2018/4/4 0004
 */
class NoScrollViewPager(context:Context,attrs: AttributeSet): ViewPager(context,attrs)  {


    private var noScroll = true


    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll)
            false
        else
            super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll)
            false
        else
            super.onInterceptTouchEvent(arg0)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }

}