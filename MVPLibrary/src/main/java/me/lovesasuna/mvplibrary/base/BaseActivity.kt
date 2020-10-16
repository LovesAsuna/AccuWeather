package me.lovesasuna.mvplibrary.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.lovesasuna.mvplibrary.BaseApplication


/**
 * @author LovesAsuna
 * @date 2020/10/10 23:43
 **/
abstract class BaseActivity : AppCompatActivity(), UICallBack {
    protected var context: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBeforeView(savedInstanceState)
        context = this
        //添加继承这个BaseActivity的Activity
        BaseApplication.activityManager.addActivity(this)
        if (getLayoutId() > 0) {
            setContentView(getLayoutId())
        }
        initData(savedInstanceState)
    }

    override fun initBeforeView(savedInstanceState: Bundle?) {}

}