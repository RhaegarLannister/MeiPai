package com.rhaegar.meipai.ui.main;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.viewpager.widget.ViewPager;

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
@BindingMethods({
        @BindingMethod(type = LongClickButton2.class,
                attribute = "app:cancelListener",
                method = "setCancelListener"),
})
public class ViewAdapter {


    @BindingAdapter("imageRes")
    public static void setImageRes(ImageView iv,int id){
        iv.setImageResource(id);
    }

    @BindingAdapter("viewPageChange")
    public static void onPageChange(ViewPager viewPager, final MainViewModel mainViewModel){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mainViewModel.changeIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
