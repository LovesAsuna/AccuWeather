package me.lovesasuna.mvplibrary.base

import android.os.Bundle

interface UICallBack {
    // 初始化savedInstanceState
    fun initBeforeView(savedInstanceState: Bundle?)

    // 初始化
    fun initData(savedInstanceState: Bundle?)

    // 布局
    fun getLayoutId(): Int
}