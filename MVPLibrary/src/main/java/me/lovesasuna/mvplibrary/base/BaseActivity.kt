package me.lovesasuna.mvplibrary.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.lovesasuna.mvplibrary.BaseApplication
import me.lovesasuna.mvplibrary.R


/**
 * @author LovesAsuna
 * @date 2020/10/10 23:43
 **/
abstract class BaseActivity : AppCompatActivity(), UICallBack {
    protected lateinit var context: BaseActivity

    // 加载弹窗
    private var mDialog: Dialog? = null

    //弹窗出现
    open fun showLoadingDialog() {
        if (mDialog == null) {
            mDialog = Dialog(context, R.style.loading_dialog)
        }
        mDialog?.setContentView(R.layout.dialog_loading)
        mDialog?.setCancelable(false)
        mDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog?.show()
    }

    //弹窗消失
    open fun dismissLoadingDialog() {
        if (mDialog != null) {
            mDialog?.dismiss()
        }
        mDialog = null
    }

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