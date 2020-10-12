package me.lovesasuna.mvplibrary.util

import android.app.Activity

/**
 * @author LovesAsuna
 * @date 2020/10/10 21:02
 **/
class ActivityManager {
    //保存所有创建的Activity
    private val allActivities: MutableList<Activity> = ArrayList()

    /**
     * 添加Activity到管理器
     *
     * @param activity activity
     */
    fun addActivity(activity: Activity?) {
        if (activity != null) {
            allActivities.add(activity)
        }
    }


    /**
     * 从管理器移除Activity
     *
     * @param activity activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            allActivities.remove(activity)
        }
    }

    /**
     * 关闭所有Activity
     */
    fun finishAll() {
        for (activity in allActivities) {
            activity.finish()
        }
    }

    fun getTaskTop(): Activity? {
        return allActivities[allActivities.size - 1]
    }
}