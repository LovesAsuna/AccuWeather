package me.lovesasuna.accuweather

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
import me.lovesasuna.mvplibrary.BaseApplication
import me.lovesasuna.mvplibrary.util.ActivityManager


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:43
 **/
class WeatherApplication : BaseApplication() {
    private var myHandler: Handler? = null
    fun getMyHandler(): Handler? {
        return myHandler
    }

    fun setMyHandler(handler: Handler?) {
        myHandler = handler
    }

    override fun onCreate() {
        super.onCreate()
        activityManager = ActivityManager()
        context = applicationContext
        weatherApplication = this
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                sActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    companion object {
        /**
         * 应用实例
         */
        var weatherApplication: WeatherApplication? = null
        private var context: Context? = null
        private var activityManager: ActivityManager? = null
        private var sActivity: Activity? = null
        val myContext: Context?
            get() = if (weatherApplication == null) null else weatherApplication!!.applicationContext

        fun getActivityManager(): ActivityManager? {
            return activityManager
        }

        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(android.R.color.darker_gray, android.R.color.black) //全局设置主题颜色
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20F)
            }
        }
    }
}
