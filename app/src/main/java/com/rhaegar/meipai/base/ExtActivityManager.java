package com.rhaegar.meipai.base;

import android.app.Activity;
import com.rhaegar.meipai.util.ComponentUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link Activity}对象集中管理
 * Created by wangmeng at 2018/6/4 14:27
 */
public class ExtActivityManager {

    private final List<Activity> activities;


    private static ExtActivityManager activityManager;

    public static final ExtActivityManager getInstance() {
        if (activityManager == null) {
            synchronized (ExtActivityManager.class) {
                if (activityManager == null) {
                    activityManager = new ExtActivityManager();
                }
            }
        }
        return activityManager;
    }

    private ExtActivityManager() {
        activities = new ArrayList<>();
    }

    /**
     * 存储指定{@link Activity}
     *
     * @param activity 继承自{@link Activity}的activity对象
     * @return true 成功，失败 false
     */
    public boolean push(final Activity activity) {
        if (!activities.contains(activity)) {
            return activities.add(activity);
        }
        return false;
    }

    /**
     * 删除指定{@link Activity}
     *
     * @param activity 继承自{@link Activity}的activity对象
     * @return true 成功，失败 false
     */
    public boolean remove(final Activity activity) {
        if (activities.contains(activity)) {
            return activities.remove(activity);
        }
        return false;
    }

    /**
     * 判断指定Activity是否存在
     *
     * @param clz {@link Class<? extends Activity>}
     * @return
     */
    public boolean isActivityExist(Class<? extends Activity> clz) {
        synchronized (ExtActivityManager.class) {
            if (!activities.isEmpty()) {
                List<Activity> list = new ArrayList<>(activities);
                for (int i = list.size() - 1; i >= 0; i--) {
                    Activity activity = list.get(i);
                    if (activity == null || activity.isFinishing()) {
                        continue;
                    }
                    if (activity.getClass().equals(clz)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 结束所有记录的{@link Activity}
     */
    public void finishAll() {
        if (!activities.isEmpty()) {
            for (Activity activity : activities) {
                if (ComponentUtils.isStateValid(activity)) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束其他{@link Activity}
     *
     * @param excludeClzs 不需要结束的{@link Class<Activity>}对象列表
     */
    @SafeVarargs
    public final void finishOther(Class<? extends Activity>... excludeClzs) {
        if (!activities.isEmpty()) {
            List<Class<? extends Activity>> excludeClzList = new ArrayList<>();
            if (excludeClzs != null) {
                excludeClzList = Arrays.asList(excludeClzs);
            }
            for (Activity activity : activities) {
                if (excludeClzList.contains(activity.getClass())) {
                    continue;
                }
                if (ComponentUtils.isStateValid(activity)) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 获取最顶上的{@link Activity}
     *
     * @return 返回最顶上的{@link Activity}
     */
    public Activity getTopActivity(boolean containsTop) {
        int lastIndex = activities.size() - 1;
        if (!containsTop) {
            lastIndex--;
        }
        for (int i = lastIndex; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.isFinishing()) {
                continue;
            }
            return activity;
        }
        return activities.get(lastIndex);
    }


}
