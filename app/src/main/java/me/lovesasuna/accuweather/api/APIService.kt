package me.lovesasuna.accuweather.api

import me.lovesasuna.accuweather.bean.*
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

    @GET("currentconditions/v1/{locationkey}?apikey=eEXWiy9raYgjP2UTQ0UD4yB1e4FCiSKP&language=zh-CN&details=true")
    fun getTodayWeather(@Path("locationkey") key: String): Call<WeatherResponse>

    @GET("forecasts/v1/daily/5day/{locationkey}?apikey=eEXWiy9raYgjP2UTQ0UD4yB1e4FCiSKP&language=zh-CN&metric=true")
    fun getWeatherForecast(@Path("locationkey") key: String): Call<ForecastResponse>

    @GET("locations/v1/cities/geoposition/search?apikey=eEXWiy9raYgjP2UTQ0UD4yB1e4FCiSKP&language=zh-CN&details=false&toplevel=false")
    fun getLocation(@Query("q") location: String): Call<LocationResponse>

    @GET("indices/v1/daily/1day/{locationkey}?apikey=eEXWiy9raYgjP2UTQ0UD4yB1e4FCiSKP&language=zh-CN&details=true")
    fun getDailyIndex(@Path("locationkey") key: String): Call<LifeStyleResponse>

    @GET("locations/v1/cities/search?apikey=eEXWiy9raYgjP2UTQ0UD4yB1e4FCiSKP&language=zh-CN")
    fun getCityResearch(@Query("q") q: String): Call<CitySearchResponse>

    /**
     * 必应每日一图
     */
    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    fun biying(): Call<BiYingImgResponse>
}