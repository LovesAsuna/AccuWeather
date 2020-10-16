package me.lovesasuna.mvplibrary.base

import java.lang.ref.WeakReference

/**
 * @author LovesAsuna
 * @date 2020/10/10 21:11
 **/
open class BasePresenter<V : BaseView> {
    private var mWeakReference: WeakReference<V>? = null

    /**
     * 关联view
     * @param v
     */
    fun attach(v: V) {
        mWeakReference = WeakReference<V>(v)
    }

    /**
     * 分离view
     * @param v
     */
    fun detach(v: V) {
        if (mWeakReference != null) {
            mWeakReference!!.clear()
            mWeakReference = null
        }
    }

    /**
     * 获取view
     * @return
     */
    fun getView(): V? {
        return if (mWeakReference != null) {
            mWeakReference!!.get()
        } else null
    }
}