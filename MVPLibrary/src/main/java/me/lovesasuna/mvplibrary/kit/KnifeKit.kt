package me.lovesasuna.mvplibrary.kit

import android.app.Activity
import android.app.Dialog
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder


/**
 * @author LovesAsuna
 * @date 2020/10/10 21:03
 **/
object KnifeKit {
    //解绑
    fun bind(target: Any): Unbinder {
        return when (target) {
            is Activity -> ButterKnife.bind(target)
            is Dialog -> ButterKnife.bind(target)
            is View -> ButterKnife.bind(target)
            else -> Unbinder.EMPTY
        }
    }

    //绑定输入目标资源
    fun bind(target: Any, source: Any): Unbinder {
        return when (source) {
            is Activity -> ButterKnife.bind(target, source)
            is Dialog -> ButterKnife.bind(target, source)
            is View -> ButterKnife.bind(target, source)
            else -> Unbinder.EMPTY
        }
    }

    //解绑
    fun unbind(unBinder: Unbinder) {
        if (unBinder !== Unbinder.EMPTY) {
            unBinder.unbind()
        }
    }
}