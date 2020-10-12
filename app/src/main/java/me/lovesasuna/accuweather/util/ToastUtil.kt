package me.lovesasuna.accuweather.util

import android.content.Context
import android.os.Build
import android.widget.Toast


object ToastUtil {
    fun showLongToast(context: Context, llw: CharSequence) {
        Toast.makeText(context.applicationContext, llw, Toast.LENGTH_LONG).show();
    }

    fun showShortToast(context: Context, llw: CharSequence) {
        Toast.makeText(context.applicationContext, llw, Toast.LENGTH_SHORT).show();
    }
}