package com.rhaegar.meipai.ui.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class PhilSeekBar extends SeekBar {
    private static InverseBindingListener mInverseBindingListener;

    public PhilSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //触发反向数据的传递
                if (mInverseBindingListener != null) {
                    mInverseBindingListener.onChange();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @BindingAdapter(value = "philprogress", requireAll = false)
    public static void setPhilProgress(PhilSeekBar seekBar, int progress) {
        if (getPhilProgress(seekBar) != progress) {
            seekBar.setProgress(progress);
        }
    }

    @InverseBindingAdapter(attribute = "philprogress", event = "philprogressAttrChanged")
    public static int getPhilProgress(PhilSeekBar seekBar) {
        return seekBar.getProgress();
    }

    @BindingAdapter(value = {"philprogressAttrChanged"}, requireAll = false)
    public static void setPhilProgressAttrChanged(PhilSeekBar seekBar, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            Log.e("错误！", "InverseBindingListener为空!");
        } else {
            mInverseBindingListener = inverseBindingListener;
        }
    }
}