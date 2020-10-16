package me.lovesasuna.mvplibrary

import android.app.Application
import android.content.Context
import me.lovesasuna.mvplibrary.util.ActivityManager


/**
 * @author LovesAsuna
 * @date 2020/10/10 23:47
 **/
open class BaseApplication : Application() {
    companion object {
        lateinit var activityManager: ActivityManager
        lateinit var application: BaseApplication
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        //声明Activity管理
        activityManager = ActivityManager()
        context = applicationContext
        application = this
    }

}