package me.lovesasuna.accuweather.mvp

import android.os.Bundle
import me.lovesasuna.accuweather.contract.WeatherContract
import me.lovesasuna.mvplibrary.base.BaseActivity
import me.lovesasuna.mvplibrary.base.BasePresenter
import me.lovesasuna.mvplibrary.base.BaseView


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:16
 **/
/**
 * 适用于需要访问网络接口的Activity
 */
abstract class MvpActivity<P : BasePresenter<WeatherContract.IWeatherView>> : BaseActivity() {
    protected var mPresent: P? = null

    override fun initBeforeView(savedInstanceState: Bundle?) {
        mPresent = createPresent()
        mPresent!!.attach(this as WeatherContract.IWeatherView)
    }

    protected abstract fun createPresent(): P

    public override fun onDestroy() {
        super.onDestroy()
        mPresent!!.detach(this as WeatherContract.IWeatherView)
    }
}