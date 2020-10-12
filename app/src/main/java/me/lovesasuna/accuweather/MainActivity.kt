package me.lovesasuna.accuweather

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.tbruyelle.rxpermissions2.RxPermissions
import me.lovesasuna.accuweather.util.ToastUtil.showShortToast
import okhttp3.*
import okio.IOException

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv_address_detail)
    lateinit var tvAddressDetail: TextView

    // 权限请求框架
    private lateinit var rxPermissions: RxPermissions

    //定位器
    lateinit var mLocationClient: LocationClient
    private val myListener: MyLocationListener = MyLocationListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.setDebug(true)
        ButterKnife.bind(this)
        rxPermissions = RxPermissions(this)
        tvAddressDetail = window.findViewById(R.id.tv_address_detail)
        permissionVersion()
    }

    //权限判断
    private fun permissionVersion() {
        //6.0或6.0以上
        if (Build.VERSION.SDK_INT >= 23) {
            //动态权限申请
            permissionsRequest()
        } else { //6.0以下
            //发现只要权限在AndroidManifest.xml中注册过，均会认为该权限granted  提示一下即可
            showShortToast(this, "你的版本在Android6.0以下，不需要动态申请权限。")
        }
    }

    //动态权限申请
    private fun permissionsRequest() {
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe { granted: Boolean ->
                // 申请成功
                if (granted) {
                    // 得到权限之后开始定位
                    startLocation()
                } else {
                    // 申请失败
                    showShortToast(this, "权限未开启")
                }
            }
    }

    // 开始定位
    private fun startLocation() {
        // 声明LocationClient类
        mLocationClient = LocationClient(this)
        // 注册监听函数
        mLocationClient.registerLocationListener(myListener)
        val option = LocationClientOption()

        // 如果开发者需要获得当前点的地址信息，此处必须为true
        option.isOpenGps = true
        option.setScanSpan(1000)
        option.setIsNeedAddress(true)
        // 可选，设置是否需要最新版本的地址信息。默认不需要，即参数为false
        option.setNeedNewVersionRgc(true)
        // mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.locOption = option
        // 启动定位
        mLocationClient.start()
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            // 获取纬度信息
            val latitude: Double = location.latitude

            // 获取经度信息
            val longitude: Double = location.longitude

            // 获取定位精度，默认值为0.0f
            val radius: Float = location.radius

            // 获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            val coorType: String = location.coorType

            println(tvAddressDetail)
            tvAddressDetail!!.text = "测试"
            getTodayWeather(longitude, latitude)
        }
    }

    // 获取今天的天气数据
    private fun getTodayWeather(longitude: Double, latitude: Double) {
        // 使用Get异步请求
        val client = OkHttpClient()
        // 拼接访问地址
        val request: Request = Request.Builder()
            .url("https://devapi.heweather.net/v7/weather/now?key=96d36fa1d4d548278bafb3002c8f7326&location=$longitude,$latitude")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) { //回调的方法执行在子线程。
                    Log.d("kwwl", "获取数据成功了")
                    Log.d("kwwl", "response.code()==" + response.code)
                    Log.d("kwwl", "response.body().string()==" + response.body?.string())
                }
            }
        })
    }
}