package me.lovesasuna.mvplibrary.mvp

import android.os.Bundle
import me.lovesasuna.mvplibrary.base.BaseFragment
import me.lovesasuna.mvplibrary.base.BasePresenter
import me.lovesasuna.mvplibrary.base.BaseView


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:34
 **/
/**
 * 适用于需要访问网络接口的Fragment
 */
abstract class MvpFragment<P : BasePresenter<BaseView>> : BaseFragment() {
    protected var mPresent: P? = null
    override fun initBeforeView(savedInstanceState: Bundle?) {
        mPresent = createPresent()
        mPresent!!.attach(this as BaseView)
    }

    override fun onDetach() {
        super.onDetach()
        if (mPresent != null) {
            mPresent!!.detach(this as BaseView)
        }
    }

    protected abstract fun createPresent(): P
}
