package me.lovesasuna.accuweather.contract

import me.lovesasuna.accuweather.MainActivity
import me.lovesasuna.accuweather.api.APIService
import me.lovesasuna.accuweather.bean.ForecastResponse
import me.lovesasuna.accuweather.bean.LocationResponse
import me.lovesasuna.accuweather.bean.WeatherResponse
import me.lovesasuna.mvplibrary.base.BasePresenter
import me.lovesasuna.mvplibrary.base.BaseView
import me.lovesasuna.mvplibrary.net.NetCallBack
import me.lovesasuna.mvplibrary.net.ServiceGenerator.createService
import retrofit2.Call
import retrofit2.Response


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:43
 **/
/**
 * 天气订阅器
 */
class WeatherContract {
    class WeatherPresenter : BasePresenter<IWeatherView>() {
        /**
         * 当日天气
         * @param context
         * @param location  区/县
         */
        fun todayWeather(context: MainActivity.MyLocationListener, location: String) {

            //得到构建之后的网络请求服务，这里的地址已经拼接完成，只差一个location了
            val service = createService(APIService::class.java)

            service.getLocation(location).enqueue(object : NetCallBack<LocationResponse>() {
                override fun onSuccess(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                    //设置请求回调  NetCallBack是重写请求回调
                    service.getTodayWeather(getView()?.getCurrentLocationResult(response)!!).enqueue(object :
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

                override fun onFailed() {
                    getView()?.dataFailed()
                }

            })

        }


        /**
         * 天气预报  3-7天(白嫖的就只能看到3天)
         * @param context
         * @param location
         */
        fun weatherForecast(context: MainActivity.MyLocationListener, location: String) {
            val service = createService(APIService::class.java)

            service.getLocation(location).enqueue(object : NetCallBack<LocationResponse>() {
                override fun onSuccess(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                    //设置请求回调  NetCallBack是重写请求回调
                    service.getWeatherForecast(getView()?.getCurrentLocationResult(response)!!).enqueue(object :
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

                override fun onFailed() {
                    getView()?.dataFailed()
                }

            })
        }

    }

    interface IWeatherView : BaseView {

        // 将数据放入实体
        fun getCurrentLocationResult(response: Response<LocationResponse>): String?

        //将数据放入实体
        fun getTodayWeatherResult(response: Response<WeatherResponse>)

        //查询天气预报的数据返回
        fun getWeatherForecastResult(response: Response<ForecastResponse>)

        //错误返回
        fun dataFailed()
    }
}