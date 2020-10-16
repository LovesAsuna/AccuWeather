package me.lovesasuna.mvplibrary.net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:37
 **/
/**
 * 网络请求回调
 * @param <T>
</T> */
abstract class NetCallBack<T> : Callback<T> {
    // 这里实现了retrofit2.Callback
    // 访问成功回调
    override fun onResponse(call: Call<T>, response: Response<T>) { //数据返回
        if (response.body() != null && response.isSuccessful) {
            onSuccess(call, response)
        } else {
            onFailed()
        }
    }

    //访问失败回调
    override fun onFailure(call: Call<T>, t: Throwable) {
        println("信息: ${t.message}")
        onFailed()
    }

    //数据返回
    abstract fun onSuccess(call: Call<T>, response: Response<T>)

    //失败异常
    abstract fun onFailed()
}