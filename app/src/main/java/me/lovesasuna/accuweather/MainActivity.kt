package me.lovesasuna.accuweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.tbruyelle.rxpermissions2.RxPermissions
import me.lovesasuna.accuweather.adapter.WeatherForecastAdapter
import me.lovesasuna.accuweather.bean.*
import me.lovesasuna.accuweather.contract.WeatherContract
import me.lovesasuna.accuweather.databinding.ActivityMainBinding
import me.lovesasuna.accuweather.mvp.MvpActivity
import me.lovesasuna.accuweather.util.ToastUtil.showShortToast
import me.lovesasuna.mvplibrary.view.WhiteWindmills
import retrofit2.Response


class MainActivity : MvpActivity<WeatherContract.WeatherPresenter>(), WeatherContract.IWeatherView {

    companion object {
        lateinit var resources: Resources
    }

    lateinit var binding: ActivityMainBinding

    lateinit var tvInfo: TextView
    lateinit var tvTemperature: TextView
    lateinit var tvLowHeight: TextView
    lateinit var tvCity: TextView
    lateinit var tvOldTime: TextView
    lateinit var rv: RecyclerView
    lateinit var mList: MutableList<DailyForecastsItem>
    lateinit var mAdapter: WeatherForecastAdapter
    lateinit var tvComf: TextView
    lateinit var tvDriving: TextView
    lateinit var tvFlu: TextView
    lateinit var tvSport: TextView
    lateinit var tvTrav: TextView
    lateinit var tvAir: TextView
    lateinit var tvWindDirection: TextView
    lateinit var tvWindPower: TextView
    lateinit var wwBig: WhiteWindmills
    lateinit var wwSmall: WhiteWindmills

    // 权限请求框架
    private lateinit var rxPermissions: RxPermissions

    //定位器
    lateinit var mLocationClient: LocationClient

    private val myListener: MyLocationListener = MyLocationListener()

    override fun initData(savedInstanceState: Bundle?) {
        rxPermissions = RxPermissions(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Companion.resources = resources
        tvInfo = binding.tvInfo
        tvTemperature = binding.tvTemperature
        tvLowHeight = binding.tvLowHeight
        tvCity = binding.tvCity
        tvOldTime = binding.tvOldTime
        rv = binding.rv
        tvComf = binding.tvComf
        tvFlu = binding.tvFlu
        tvSport = binding.tvSport
        tvTrav = binding.tvTrav
        tvDriving = binding.tvDriving
        tvAir = binding.tvAir
        tvWindDirection = binding.tvWindDirection
        tvWindPower = binding.tvWindPower
        wwBig = binding.wwBig
        wwSmall = binding.wwSmall
        initList()
        setContentView(binding.root)
        permissionVersion()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
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

    /**
     * 初始化天气预报数据列表
     */
    private fun initList() {
        mList = ArrayList() //声明为ArrayList
        mAdapter = WeatherForecastAdapter(R.layout.item_weather_forecast_list, mList) //为适配器设置布局和数据源
        val manager = LinearLayoutManager(context) //布局管理,默认是纵向
        rv.layoutManager = manager //为列表配置管理器
        rv.adapter = mAdapter //为列表配置适配器
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
        option.setScanSpan(1000 * 60)
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

            val location = "$latitude,$longitude"
            mPresent!!.todayWeather(this, location)
            mPresent!!.weatherForecast(this, location)
            mPresent!!.lifeStyle(this, location)
        }
    }

    override fun createPresent(): WeatherContract.WeatherPresenter {
        return WeatherContract.WeatherPresenter()
    }

    // 查询位置，请求成功后的数据返回
    override fun getCurrentLocationResult(response: Response<LocationResponse>): String? {
        //数据返回后关闭定位
        mLocationClient.stop()
        return if (response.isSuccessful) {
            // 城市
            val city = "${response.body()?.parentCity?.localizedName} ${response.body()?.localizedName}"
            tvCity.text = city
            tvCity.forceLayout()
            response.body()?.key
        } else null
    }


    // 查询当天天气，请求成功后的数据返回
    @SuppressLint("SetTextI18n")
    override fun getTodayWeatherResult(response: Response<WeatherResponse>) {
        //数据渲染显示出来
        tvTemperature.text = response.body()?.get(0)?.realFeelTemperature?.metric?.value.toString()//温度
        tvInfo.text = response.body()?.get(0)?.weatherText//天气状况
        tvWindDirection.text = "风向     " + response.body()?.get(0)?.wind?.direction?.localized//风向
        tvWindPower.text = "风力     " + response.body()?.get(0)?.wind?.speed?.metric?.value + "级"//风力
        tvOldTime.text = "上次更新时间：" + response.body()?.get(0)?.localObservationDateTime
    }

    override fun getWeatherForecastResult(response: Response<ForecastResponse>) {
        //最低温和最高温
        val text = "${response.body()?.dailyForecasts?.get(0)?.temperature?.minimum?.value} / ${
            response.body()?.dailyForecasts?.get(0)?.temperature?.maximum?.value
        }℃"
        tvLowHeight.text = text
        if (response.body()?.dailyForecasts?.get(0) != null) {
            val data: List<DailyForecastsItem> = response.body()?.dailyForecasts!!
            mList.clear() //添加数据之前先清除
            mList.addAll(data) //添加数据
            mAdapter.notifyDataSetChanged() //刷新列表
        } else {
            showShortToast(this, "天气预报数据为空")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLifeStyleResult(response: Response<LifeStyleResponse>) {
        val data = response.body()!!

        tvComf.text = "舒适度: ${data[17].text}"
        tvTrav.text = "旅游: ${data[32].text}"
        tvSport.text = "户外运动: ${data[29].text}"
        tvDriving.text = "驾车: ${data[40].text}"
        tvFlu.text = "流感: ${data[26].text}"
        tvAir.text = "空气质量: ${data[19].text}"

    }


    //数据请求失败返回
    override fun dataFailed() {
        showShortToast(this, "网络异常") //这里的context是框架中封装好的，等同于this
    }

}