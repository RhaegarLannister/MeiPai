package com.rhaegar.meipai.ui.main;

import android.view.View;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.viewpager.widget.ViewPager;
import com.rhaegar.meipai.R;

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


    @BindingAdapter({"imageBac","imageBacRes"})
    public static void setImageRes(View iv, boolean select,int res){
        if (iv.isPressed()||select){
            if (res==1){
                iv.setBackgroundResource(R.drawable.icon_arow_left_light);
            }else {
                iv.setBackgroundResource(R.drawable.icon_arow_right_light);
            }
        }else {
            if (res==1){
                iv.setBackgroundResource(R.drawable.ic_arow_left);
            }else {
                iv.setBackgroundResource(R.drawable.ic_arow_right);
            }
        }
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
