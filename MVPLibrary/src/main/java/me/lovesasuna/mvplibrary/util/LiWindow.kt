package me.lovesasuna.mvplibrary.util

import android.app.Activity
import android.content.Context
import android.view.*
import android.widget.PopupWindow
import me.lovesasuna.mvplibrary.R


/**
 * 自定义弹窗
 *
 * @author LovesAsuna
 * @date 2020/10/25 9:43
 **/
class LiWindow {
    private lateinit var mLiWindow: LiWindow
    private lateinit var mPopupWindow: PopupWindow
    private lateinit var inflater: LayoutInflater
    private lateinit var mView: View
    private lateinit var mContext: Context
    private lateinit var show: WindowManager
    var context: WindowManager.LayoutParams? = null
    private var mMap: Map<String, Any> = HashMap()
    fun getmMap(): Map<String, Any> {
        return mMap
    }

    constructor(context: Context) {
        mContext = context
        inflater = LayoutInflater.from(context)
        mLiWindow = this
    }

    constructor(context: Context, map: Map<String, Any>) {
        mContext = context
        mMap = map
        inflater = LayoutInflater.from(context)
    }

    /**
     * 右侧显示  自适应大小
     * @param mView
     */
    fun showRightPopupWindow(mView: View?) {
        mPopupWindow = PopupWindow(
            mView,
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        mPopupWindow.contentView = mView
        mPopupWindow.isOutsideTouchable = true //点击空白处不关闭弹窗  true为关闭
        mPopupWindow.isFocusable = true
        mPopupWindow.animationStyle = R.style.AnimationRightFade //设置动画
        mPopupWindow.showAtLocation(mView, Gravity.RIGHT, 0, 0)
        setBackgroundAlpha(0.5f, mContext)
        val nomal = (mContext as Activity).window.attributes
        nomal.alpha = 0.5f
        (mContext as Activity).window.attributes = nomal
        mPopupWindow.setOnDismissListener(closeDismiss)
    }

    /**
     * 右侧显示  高度占满父布局
     * @param mView
     */
    fun showRightPopupWindowMatchParent(mView: View?) {
        mPopupWindow = PopupWindow(
            mView,
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true
        )
        mPopupWindow.contentView = mView
        mPopupWindow.isOutsideTouchable = true //点击空白处不关闭弹窗  true为关闭
        mPopupWindow.isFocusable = true
        mPopupWindow.animationStyle = R.style.AnimationRightFade //设置动画
        mPopupWindow.showAtLocation(mView, Gravity.RIGHT, 0, 0)
        setBackgroundAlpha(0.5f, mContext)
        val nomal = (mContext as Activity).window.attributes
        nomal.alpha = 0.5f
        (mContext as Activity).window.attributes = nomal
        mPopupWindow.setOnDismissListener(closeDismiss)
    }

    /**
     * 底部显示
     * @param mView
     */
    fun showBottomPopupWindow(mView: View?) {
        mPopupWindow = PopupWindow(
            mView,
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        mPopupWindow.contentView = mView
        mPopupWindow.isOutsideTouchable = true //点击空白处不关闭弹窗  true为关闭
        mPopupWindow.isFocusable = true
        mPopupWindow.animationStyle = R.style.AnimationBottomFade //设置动画
        mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0)
        setBackgroundAlpha(0.5f, mContext)
        val nomal = (mContext as Activity).window.attributes
        nomal.alpha = 0.5f
        (mContext as Activity).window.attributes = nomal
        mPopupWindow.setOnDismissListener(closeDismiss)
    }

    /**
     * 设置弹窗动画
     * @param animId
     * @return showPopu
     */
    fun setAnim(animId: Int): LiWindow? {
        if (mPopupWindow != null) {
            mPopupWindow.animationStyle = animId
        }
        return mLiWindow
    }

    //弹窗消失时关闭阴影
    var closeDismiss = PopupWindow.OnDismissListener {
        val nomal = (mContext as Activity).window.attributes
        nomal.alpha = 1f
        (mContext as Activity).window.attributes = nomal
    }

    fun closePopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss()
        }
    }
    /*
        使用方法
    *   LiWindow liWindow = new LiWindow(MainActivity.this);
        View mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.center_layout,null);
        liWindow.showCenterPopupWindow(mView);
    * */

    companion object {
        fun setBackgroundAlpha(bgAlpha: Float, mContext: Context) {
            val lp = (mContext as Activity).window.attributes
            lp.alpha = bgAlpha
            (mContext as Activity).window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            (mContext as Activity).window.attributes = lp
        }
    }
}