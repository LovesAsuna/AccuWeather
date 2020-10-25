package me.lovesasuna.mvplibrary.util

import android.content.Context
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import me.lovesasuna.mvplibrary.R


/**
 * 动画RecycleView
 *
 * @author LovesAsuna
 * @date 2020/10/25 15:15
 **/
object RecyclerViewAnimation {
    //数据变化时显示动画  底部动画
    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context: Context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    //数据变化时显示动画  右侧动画
    fun runLayoutAnimationRight(recyclerView: RecyclerView) {
        val context: Context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}