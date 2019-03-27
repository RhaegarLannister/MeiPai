package com.rhaegar.meipai.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.*;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * 透明状态栏
 * Created by wangmeng at 2018/6/19 09:30
 */
public class TranslucentUtils {

    /**
     * 获取内容区
     *
     * @param activity {@link Activity}
     * @return {@link ViewGroup}
     */
    private static ViewGroup getContentView(Activity activity) {
        ViewGroup rootView = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上和可以透明状态栏
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0换一种方式（全透明）
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            if (decorViewGroup != null) {
                rootView = decorViewGroup.findViewById(android.R.id.content);
            }
        }
        return rootView;
    }

    /**
     * 获取状态栏占位视图
     *
     * @param rootView  {@link ViewGroup} android.R.id.content
     * @param activity  {@link Activity}
     * @param fitSystem 是否适配系统UI
     * @return {@link View}
     */
    private static View getStatusBarTintView(ViewGroup rootView, Activity activity, boolean fitSystem) {
        View statusBarTintView = null;
        if (rootView != null) {
            View contentView = rootView.getChildAt(0);
            if (contentView != null) {
                contentView.setFitsSystemWindows(fitSystem);
            }
            //用来设置状态样背景色
            statusBarTintView = rootView.getChildAt(1);
            if (statusBarTintView == null) {
                statusBarTintView = new View(rootView.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
                params.gravity = Gravity.TOP;
                statusBarTintView.setLayoutParams(params);
            }
        }
        return statusBarTintView;
    }


    /**
     * 设置状态栏背景色
     *
     * @param activity  {@link Activity}
     * @param fitSystem 是否适配系统UI
     * @param color     @ColorInt
     */
    public static void setStatusBarBackgroundColor(Activity activity, @ColorInt int color, boolean fitSystem) {
        ViewGroup rootView = getContentView(activity);
        if (rootView != null) {
            View statusBarTintView = getStatusBarTintView(rootView, activity, fitSystem);
            if (statusBarTintView != null) {
                statusBarTintView.setBackgroundColor(color);
                if (statusBarTintView.getParent() == null) {
                    rootView.addView(statusBarTintView);
                }
            }
        }
    }

    /**
     * 设置状态栏背景色
     *
     * @param activity   {@link Activity}
     * @param fitSystem  是否适配系统UI
     * @param background {@Drawable}
     */
    public static void setStatusBarBackground(Activity activity, Drawable background, boolean fitSystem) {
        ViewGroup rootView = getContentView(activity);
        if (rootView != null) {
            View statusBarTintView = getStatusBarTintView(rootView, activity, fitSystem);
            if (statusBarTintView != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    statusBarTintView.setBackground(background);
                }
                if (statusBarTintView.getParent() == null) {
                    rootView.addView(statusBarTintView);
                }
            }
        }
    }

    /**
     * 设置状态栏背景色
     *
     * @param activity        {@link Activity}
     * @param fitSystem       是否适配系统UI
     * @param backgroundResId @DrawableRes
     */
    public static void setStatusBarBackgroundResource(Activity activity, @DrawableRes int backgroundResId, boolean fitSystem) {
        ViewGroup rootView = getContentView(activity);
        if (rootView != null) {
            View statusBarTintView = getStatusBarTintView(rootView, activity, fitSystem);
            if (statusBarTintView != null) {
                statusBarTintView.setBackgroundResource(backgroundResId);
                if (statusBarTintView.getParent() == null) {
                    rootView.addView(statusBarTintView);
                }
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param activity {@link Activity}
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
