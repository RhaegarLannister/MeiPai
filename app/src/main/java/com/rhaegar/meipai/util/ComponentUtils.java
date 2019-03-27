package com.rhaegar.meipai.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 组件工具类，处理例如组件状态检测、組件装载等，组件包括{@link Fragment}、{@link FragmentActivity}等<br/>
 * Created by wangmeng at 2018/6/1 14:45
 */
public class ComponentUtils {

    /**
     * 检测{@link Context}是否有效
     *
     * @param context {@link Context}对象
     * @return true有效, false无效
     */
    public static boolean isStateValid(Context context) {
        if (context instanceof Activity) {
            return isStateValid((Activity) context);
        }
        return false;
    }

    /**
     * 检测{@link FragmentActivity}是否有效
     *
     * @param activity {@link FragmentActivity}对象
     * @return true有效, false无效
     */
    public static boolean isStateValid(Activity activity) {
        boolean isValid = activity != null && !activity.isFinishing();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isValid = isValid && !activity.isDestroyed();
        }
        return isValid;
    }

    /**
     * 检测{@link Fragment}是否有效
     *
     * @param fragment {@link Fragment}对象
     * @return true有效, false无效
     */
    public static boolean isStateValid(Fragment fragment) {
        if (fragment != null && !fragment.isDetached() && !fragment.isRemoving()) {
            return isStateValid(fragment.getActivity());
        }
        return false;
    }

    /**
     * 从宿主{@link Fragment}中创建{@link Fragment}的优化处理
     *
     * @param fragment 需要创建的{@link Fragment}
     * @param frameId  需要创建的{@link Fragment}的父级容器中位置id
     */
    public static void addSubFrament(Fragment parent, Fragment fragment, int frameId, Bundle bundle) {
        if (isStateValid(parent)) {
            FragmentManager manager = parent.getChildFragmentManager();
            FragmentTransaction beginTransaction = manager.beginTransaction();
            fragment.setArguments(bundle);
            beginTransaction.replace(frameId, fragment);
            beginTransaction.commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
    }


    /**
     * 从宿主{@link FragmentActivity}中创建{@link Fragment}的优化处理
     *
     * @param fragment     被操作的{@link Fragment}
     * @param hostActivity 宿主{@link FragmentActivity}
     * @param frameId      需要创建的{@link Fragment}的父级容器中位置id
     */
    public static void initFrament(Fragment fragment, FragmentActivity hostActivity, int frameId) {
        initFrament(fragment, hostActivity, frameId, null);
    }


    /**
     * 从宿主{@link FragmentActivity}中创建{@link Fragment}的优化处理
     *
     * @param fragment     被操作的{@link Fragment}
     * @param hostActivity 宿主{@link FragmentActivity}
     * @param frameId      需要创建的{@link Fragment}的父级容器中位置id
     */
    public static void initFrament(Fragment fragment, FragmentActivity hostActivity, int frameId, Bundle bundle) {
        if (fragment != null && isStateValid(hostActivity)) {
            FragmentManager manager = hostActivity.getSupportFragmentManager();
            FragmentTransaction beginTransaction = manager.beginTransaction();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            beginTransaction.replace(frameId, fragment);
            beginTransaction.commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
    }



}
