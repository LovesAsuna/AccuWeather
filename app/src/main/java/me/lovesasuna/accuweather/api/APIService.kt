package me.lovesasuna.accuweather.api

import me.lovesasuna.accuweather.bean.ForecastResponse
import me.lovesasuna.accuweather.bean.LocationResponse
import me.lovesasuna.accuweather.bean.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author LovesAsuna
 * @date 2020/10/14 23:41
 *
 * API服务接口
 */
interface APIService {

    @GET("currentconditions/v1/{locationkey}?apikey=zv7XGQbr8wegduK3zh12G0mz3y540k9z&language=zh-CN&details=true")
    fun getTodayWeather(@Path("locationkey") key: String): Call<WeatherResponse>

    @GET("forecasts/v1/daily/5day/{locationkey}?apikey=zv7XGQbr8wegduK3zh12G0mz3y540k9z&language=zh-CN")
    fun getWeatherForecast(@Path("locationkey") key: String): Call<ForecastResponse>

    @GET("locations/v1/cities/geoposition/search?apikey=zv7XGQbr8wegduK3zh12G0mz3y540k9z&language=zh-CN&details=false&toplevel=false")
    fun getLocation(@Query("q") location: String): Call<LocationResponse>

}