package com.rhaegar.meipai.ui.main


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rhaegar.meipai.R

/**
 * Author: Li Hai Kun
 * Description: 首页标签
 * Date: 2017/3/21
 */
class MainTabAdapter(
    fm: FragmentManager,
    private val fragments: MutableList<out Fragment> = mutableListOf(StartPositionFragment(),VedioFragment(), DelayFragment()),
    val icons: MutableList<Int> = mutableListOf(R.drawable.bg_start_position, R.drawable.bg_vedio, R.drawable.bg_delay)
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return "hah"
    }


}