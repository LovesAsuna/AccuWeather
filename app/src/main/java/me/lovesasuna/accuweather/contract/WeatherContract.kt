package me.lovesasuna.accuweather.contract

import android.app.Activity
import android.content.Context
import android.util.Log
import me.lovesasuna.accuweather.MainActivity
import me.lovesasuna.accuweather.api.APIService
import me.lovesasuna.accuweather.bean.*
import me.lovesasuna.mvplibrary.base.BasePresenter
import me.lovesasuna.mvplibrary.base.BaseView
import me.lovesasuna.mvplibrary.net.NetCallBack
import me.lovesasuna.mvplibrary.net.ServiceGenerator.createService
import retrofit2.Call
import retrofit2.Response


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:43
 *
 *
 * 天气订阅器
 */
class WeatherContract {
    class WeatherPresenter : BasePresenter<IWeatherView>() {


        /**
         * 当日天气
         * @param context
         * @param location  区/县
         */
        fun todayWeather(context: Activity, location: String) {
            val service = createService(APIService::class.java, 0)
            actionByLocation(service, location) {
                //设置请求回调  NetCallBack是重写请求回调
                service.getTodayWeather(getView()?.getCurrentLocationResult(it)!!).enqueue(object :
                    NetCallBack<WeatherResponse>() {
                    //成功回调
                    override fun onSuccess(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (getView() != null) {
                            //当视图不会空时返回请求数据
                            getView()?.getTodayWeatherResult(response)
                        }
                    }

                    //失败回调
                    override fun onFailed() {
                        if (getView() != null) { //当视图不会空时获取错误信息
                            getView()?.dataFailed()
                        }
                    }
                })
            }

        }


        /**
         * 天气预报  3-7天(白嫖的就只能看到3天)
         * @param context
         * @param location
         */
        fun weatherForecast(context: Activity, location: String) {
            val service = createService(APIService::class.java, 0)
            actionByLocation(service, location) {
                //设置请求回调  NetCallBack是重写请求回调
                service.getWeatherForecast(getView()?.getCurrentLocationResult(it)!!).enqueue(object :
                    NetCallBack<ForecastResponse>() {
                    //成功回调
                    override fun onSuccess(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                        if (getView() != null) {
                            //当视图不会空时返回请求数据
                            getView()?.getWeatherForecastResult(response)
                        }
                    }

                    //失败回调
                    override fun onFailed() {
                        if (getView() != null) { //当视图不会空时获取错误信息
                            getView()?.dataFailed()
                        }
                    }
                })
            }
        }

        /**
         * 生活指数
         * @param context
         * @param location
         */
        fun lifeStyle(context: Activity, location: String) {
            val service = createService(APIService::class.java, 0)
            actionByLocation(service, location) {
                service.getDailyIndex(getView()?.getCurrentLocationResult(it)!!)
                    .enqueue(object : NetCallBack<LifeStyleResponse>() {
                        override fun onSuccess(call: Call<LifeStyleResponse>, response: Response<LifeStyleResponse>) {
                            if (getView() != null) {
                                getView()!!.getLifeStyleResult(response)
                            }
                        }

                        override fun onFailed() {
                            if (getView() != null) {
                                getView()?.dataFailed()
                            }
                        }
                    })
            }
        }

        fun citySearch(q: String, callBack: (response: Response<CitySearchResponse>) -> Unit) {
            val service = createService(APIService::class.java, 0)
            service.getCityResearch(q).enqueue(object : NetCallBack<CitySearchResponse>() {
                override fun onSuccess(call: Call<CitySearchResponse>, response: Response<CitySearchResponse>) {
                    callBack.invoke(response)
                }

                override fun onFailed() {
                    if (getView() != null) {
                        getView()?.dataFailed()
                    }
                }
            })
        }

        fun biying(context: Context) {
            val service = createService(APIService::class.java, 1)
            service.biying().enqueue(object : NetCallBack<BiYingImgResponse>() {
                override fun onSuccess(call: Call<BiYingImgResponse>, response: Response<BiYingImgResponse>) {
                    if (getView() != null) {
                        getView()!!.getBiYingResult(response)
                    }
                }

                override fun onFailed() {

                }

            })
        }

        private fun actionByLocation(
            service: APIService,
            location: String,
            action: (response: Response<LocationResponse>) -> Unit
        ) {
            service.getLocation(location).enqueue(object : NetCallBack<LocationResponse>() {
                override fun onSuccess(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                    action.invoke(response)
                }

                override fun onFailed() {
                    getView()?.dataFailed()
                }

            })

        }

    }

    interface IWeatherView : BaseView {

        // 将数据放入实体
        fun getCurrentLocationResult(response: Response<LocationResponse>): String?

        // 将数据放入实体
        fun getTodayWeatherResult(response: Response<WeatherResponse>)

        // 查询天气预报的数据返回
        fun getWeatherForecastResult(response: Response<ForecastResponse>)

        // 查询生活指数的数据返回
        fun getLifeStyleResult(response: Response<LifeStyleResponse>)

        // 错误返回
        fun dataFailed()

        // 获取必应每日一图返回
        fun getBiYingResult(response: Response<BiYingImgResponse>)
    }
}
