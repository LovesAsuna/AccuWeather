package me.lovesasuna.mvplibrary.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.Unbinder
import me.lovesasuna.mvplibrary.BaseApplication
import me.lovesasuna.mvplibrary.kit.KnifeKit


/**
 * @author LovesAsuna
 * @date 2020/10/10 23:43
 **/
abstract class BaseActivity : AppCompatActivity(), UICallBack {
    private var context: Activity? = null
    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            initBeforeView(it)
        }
        context = this
        //添加继承这个BaseActivity的Activity
        BaseApplication.activityManager.addActivity(this)
        if (getLayoutId() > 0) {
            setContentView(getLayoutId())
            unbinder = KnifeKit.bind(this)
        }
        savedInstanceState?.let {
            initData(it)
        }
    }

    override fun initBeforeView(savedInstanceState: Bundle) {}

    override fun onStart() {
        super.onStart()
    }
}