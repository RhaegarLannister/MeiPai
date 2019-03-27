package com.rhaegar.meipai.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Author: Li Hai Kun
 * Description:不可滑动的ViewPager
 * Date: 2018/4/4 0004
 */
class NoScrollViewPager(context:Context,attrs: AttributeSet): ViewPager(context,attrs)  {


    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        return true
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)
    }

}