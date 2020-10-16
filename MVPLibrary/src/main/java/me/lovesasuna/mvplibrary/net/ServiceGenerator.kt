package me.lovesasuna.mvplibrary.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author LovesAsuna
 * @date 2020/10/14 23:36
 **/
/**
 * 服务构建器 API服务设置在里面
 */
object ServiceGenerator {
    // https://free-api.heweather.net/s6/weather/now?key=3086e91d66c04ce588a7f538f917c7f4&location=深圳
    // 将上方的API接口地址进行拆分得到不变的一部分,实际开发中可以将这一部分作为服务器的ip访问地址
    private const val BASE_URL = "http://dataservice.accuweather.com"

    // 创建服务  参数就是API服务
    fun <T> createService(serviceClass: Class<T>): T {

        // 创建OkHttpClient构建器对象
        val okHttpClientBuilder = OkHttpClient.Builder()

        // 设置请求超时的时间，这里是10秒
        okHttpClientBuilder.connectTimeout(10000, TimeUnit.MILLISECONDS)

        // 消息拦截器  因为有时候接口不同在排错的时候 需要先从接口的响应中做分析。利用了消息拦截器可以清楚的看到接口返回的所有内容
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        // setlevel用来设置日志打印的级别，共包括了四个级别：NONE,BASIC,HEADER,BODY
        // BASEIC:请求/响应行
        // HEADER:请求/响应行 + 头
        // BODY:请求/响应航 + 头 + 体
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        // 为OkHttp添加消息拦截器
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        // 在Retrofit中设置httpclient
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL) // 设置地址  就是上面的固定地址,如果你是本地访问的话，可以拼接上端口号  例如 +":8080"
            .addConverterFactory(JacksonConverterFactory.create()) // 用Gson把服务端返回的json数据解析成实体
            .client(okHttpClientBuilder.build()) // 放入OKHttp，之前说过retrofit是对OkHttp的进一步封装
            .build()
        return retrofit.create(serviceClass) // 返回这个创建好的API服务
    }
}