package me.lovesasuna.accuweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tbruyelle.rxpermissions2.RxPermissions
import me.lovesasuna.accuweather.adapter.AreaAdapter
import me.lovesasuna.accuweather.adapter.CityAdapter
import me.lovesasuna.accuweather.adapter.ProvinceAdapter
import me.lovesasuna.accuweather.adapter.WeatherForecastAdapter
import me.lovesasuna.accuweather.bean.*
import me.lovesasuna.accuweather.bean.CityResponse.CityBean
import me.lovesasuna.accuweather.bean.CityResponse.CityBean.AreaBean
import me.lovesasuna.accuweather.contract.WeatherContract
import me.lovesasuna.accuweather.databinding.ActivityMainBinding
import me.lovesasuna.accuweather.mvp.MvpActivity
import me.lovesasuna.accuweather.util.ToastUtil.showShortToast
import me.lovesasuna.mvplibrary.util.LiWindow
import me.lovesasuna.mvplibrary.util.RecyclerViewAnimation.runLayoutAnimationRight
import me.lovesasuna.mvplibrary.view.CircleView
import me.lovesasuna.mvplibrary.view.WhiteWindmills
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


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
    lateinit var list: MutableList<String>
    lateinit var provinceList: MutableList<CityResponse>
    lateinit var cityList: MutableList<CityBean>
    lateinit var areaList: MutableList<AreaBean>
    lateinit var provinceAdapter: ProvinceAdapter
    lateinit var cityAdapter: CityAdapter
    lateinit var areaAdapter: AreaAdapter
    var provinceTitle: String = ""
    lateinit var liWindow: LiWindow
    lateinit var citySelect: ImageView
    lateinit var bg: LinearLayout

    lateinit var tvComf: TextView
    lateinit var tvDriving: TextView
    lateinit var tvFlu: TextView
    lateinit var tvSport: TextView
    lateinit var tvTrav: TextView
    lateinit var tvAir: TextView
    lateinit var tvWindDirection: TextView
    lateinit var tvWindPower: TextView
    lateinit var ivLocation: ImageView

    // 图标显示标识,true显示，false不显示,只有定位的时候才为true,切换城市和常用城市都为false
    private var flag = true

    lateinit var waveView: CircleView
    lateinit var tvPm25: TextView
    lateinit var tvO3: TextView
    lateinit var tvCo: TextView
    lateinit var wwBig: WhiteWindmills
    lateinit var wwSmall: WhiteWindmills
    lateinit var refresh: SmartRefreshLayout

    // 权限请求框架
    private lateinit var rxPermissions: RxPermissions

    // 区/县  改为全局的静态变量,方便更换城市之后也能进行下拉刷新
    private var district: String? = null

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
        waveView = binding.waveView
        waveView.setmWaterLevel(0.7F)
        // 开始执行
        waveView.startWave()
        tvPm25 = binding.tvPm25
        tvO3 = binding.tvO3
        tvCo = binding.tvCo
        wwBig = binding.wwBig
        wwSmall = binding.wwSmall
        bg = binding.bg
        refresh = binding.refresh
        ivLocation = binding.ivLocation

        citySelect = binding.ivCitySelect.also {
            it.setOnClickListener {
                showCityWindow()
            }
        }
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

    /**
     * 省市县数据渲染
     * @param recyclerView  列表
     * @param areaBack 区县返回
     * @param cityBack 市返回
     * @param windowTitle  窗口标题
     */
    private fun initCityData(
        recyclerView: RecyclerView,
        areaBack: ImageView,
        cityBack: ImageView,
        windowTitle: TextView
    ) {
        //初始化省数据 读取省数据并显示到列表中
        try {
            val inputStream: InputStream = resources.assets.open("City.txt") //读取数据
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuffer = StringBuffer()
            var lines: String? = bufferedReader.readLine()
            while (lines != null) {
                stringBuffer.append(lines)
                lines = bufferedReader.readLine()
            }
            val Data = JSONArray(stringBuffer.toString())
            //循环这个文件数组、获取数组中每个省对象的名字
            for (i in 0 until Data.length()) {
                val provinceJsonObject = Data.getJSONObject(i)
                val provinceName = provinceJsonObject.getString("name")
                val response = CityResponse()
                response.setName(provinceName)
                provinceList.add(response)
            }

            //定义省份显示适配器
            provinceAdapter = ProvinceAdapter(R.layout.item_city_list, provinceList)
            val manager = LinearLayoutManager(context)
            recyclerView.layoutManager = manager
            recyclerView.adapter = provinceAdapter
            provinceAdapter.notifyDataSetChanged()
            runLayoutAnimationRight(recyclerView) //动画展示
            provinceAdapter.setOnItemChildClickListener { adapter, view, position ->
                try {
                    //返回上一级数据
                    cityBack.visibility = View.VISIBLE
                    cityBack.setOnClickListener {
                        recyclerView.adapter = provinceAdapter
                        provinceAdapter.notifyDataSetChanged()
                        cityBack.visibility = View.GONE
                        windowTitle.text = "中国"
                    }

                    //根据当前位置的省份所在的数组位置、获取城市的数组
                    val provinceObject = Data.getJSONObject(position)
                    windowTitle.text = provinceList[position].getName()
                    provinceTitle = provinceList[position].getName()!!
                    val cityArray = provinceObject.getJSONArray("city")

                    //更新列表数据
                    cityList.clear()

                    for (i in 0 until cityArray.length()) {
                        val cityObj = cityArray.getJSONObject(i)
                        val cityName = cityObj.getString("name")
                        val response = CityBean()
                        response.name = cityName
                        cityList.add(response)
                    }
                    cityAdapter = CityAdapter(R.layout.item_city_list, cityList)
                    val manager1 = LinearLayoutManager(context)
                    recyclerView.layoutManager = manager1
                    recyclerView.adapter = cityAdapter
                    cityAdapter.notifyDataSetChanged()
                    runLayoutAnimationRight(recyclerView)
                    cityAdapter.setOnItemChildClickListener { adapter, view, position ->
                        try {
                            //返回上一级数据
                            areaBack.visibility = View.VISIBLE
                            areaBack.setOnClickListener {
                                recyclerView.adapter = cityAdapter
                                cityAdapter.notifyDataSetChanged()
                                areaBack.visibility = View.GONE
                                windowTitle.text = provinceTitle
                                areaList.clear()
                            }
                            //根据当前城市数组位置 获取地区数据
                            windowTitle.text = cityList[position].name
                            val cityJsonObj = cityArray.getJSONObject(position)
                            val areaJsonArray = cityJsonObj.getJSONArray("area")
                            areaList.clear()
                            list.clear()
                            for (i in 0 until areaJsonArray.length()) {
                                list.add(areaJsonArray.getString(i))
                            }
                            Log.i("list", list.toString())
                            for (j in list.indices) {
                                val response = AreaBean()
                                response.name = list[j]
                                areaList.add(response)
                            }
                            areaAdapter = AreaAdapter(R.layout.item_city_list, areaList)
                            val manager2 = LinearLayoutManager(context)
                            recyclerView.layoutManager = manager2
                            recyclerView.adapter = areaAdapter
                            areaAdapter.notifyDataSetChanged()
                            runLayoutAnimationRight(recyclerView)
                            areaAdapter.setOnItemChildClickListener { adapter, view, position ->
                                mPresent!!.citySearch(areaList[position].name!!) {
                                    district = "${it.body()?.get(0)?.geoPosition?.latitude},${
                                        it.body()?.get(0)?.geoPosition?.longitude
                                    }"
                                    mPresent!!.todayWeather(context, district!!) //今日天气
                                    mPresent!!.weatherForecast(context, district!!) //天气预报
                                    mPresent!!.lifeStyle(context, district!!) //生活指数
                                    // 切换城市得到的城市不属于定位，因此这里隐藏定位图标
                                    flag = false
                                    liWindow.closePopupWindow()
                                }

                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 城市弹窗
     */
    private fun showCityWindow() {
        provinceList = ArrayList()
        cityList = ArrayList()
        areaList = ArrayList()
        list = ArrayList()
        liWindow = LiWindow(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.window_city_list, null)
        val areaBack: ImageView = view.findViewById(R.id.iv_back_area) as ImageView
        val cityBack: ImageView = view.findViewById(R.id.iv_back_city) as ImageView
        val windowTitle = view.findViewById(R.id.tv_title) as TextView
        val recyclerView = view.findViewById(R.id.rv) as RecyclerView
        liWindow.showRightPopupWindow(view)
        initCityData(recyclerView, areaBack, cityBack, windowTitle)//加载城市列表数据
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

            // 在数据请求前加载等待弹窗，返回结果后关闭弹窗
            showLoadingDialog()

            district = "$latitude,$longitude"
            mPresent!!.todayWeather(context, district!!)
            mPresent!!.weatherForecast(context, district!!)
            mPresent!!.lifeStyle(context, district!!)
            mPresent!!.biying(context)

            refresh.setOnRefreshListener {
                mPresent!!.todayWeather(context, district!!) //今日天气
                mPresent!!.weatherForecast(context, district!!) //天气预报
                mPresent!!.lifeStyle(context, district!!) //生活指数
            }
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
        // 关闭弹窗
        dismissLoadingDialog()
        //数据渲染显示出来
        tvTemperature.text = response.body()?.get(0)?.realFeelTemperature?.metric?.value.toString()//温度
        if (flag) {
            ivLocation.visibility = View.VISIBLE
        } else {
            ivLocation.visibility = View.GONE
        }

        tvInfo.text = response.body()?.get(0)?.weatherText//天气状况
        tvWindDirection.text = "风向     " + response.body()?.get(0)?.wind?.direction?.localized//风向
        tvWindPower.text = "风力     " + response.body()?.get(0)?.wind?.speed?.metric?.value + "级"//风力
        tvOldTime.text = "上次更新时间：" + response.body()?.get(0)?.localObservationDateTime

        tvPm25.text = "20"//PM2.5
        tvO3.text = "25"//臭氧
        tvCo.text = "0.5"//一氧化碳
    }

    override fun getWeatherForecastResult(response: Response<ForecastResponse>) {
        //最低温和最高温
        val text = "${response.body()?.dailyForecasts?.get(0)?.temperature?.minimum?.value} / ${
            response.body()?.dailyForecasts?.get(0)?.temperature?.maximum?.value
        }℃"
        tvLowHeight.text = text
        if (response.body()?.dailyForecasts?.get(0) != null) {
            val data: List<DailyForecastsItem> = response.body()?.dailyForecasts!!
            data.forEach {
                it.date = it.date?.replace(Regex("T.*"), "")
            }
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
        // 关闭刷新
        refresh.finishRefresh()
        // 关闭弹窗
        dismissLoadingDialog()
        showShortToast(this, "网络异常") //这里的context是框架中封装好的，等同于this
    }

    override fun getBiYingResult(response: Response<BiYingImgResponse>) {
        dismissLoadingDialog()
        if (response.body()?.images != null) {
            //得到的图片地址是没有前缀的，所以加上前缀否则显示不出来
            val imgUrl = "http://cn.bing.com" + response.body()?.images?.get(0)?.url
            Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                    ) {
                        val drawable: Drawable = BitmapDrawable(context.resources, resource)
                        bg.background = drawable
                    }
                })
        } else {
            showShortToast(context, "数据为空")
        }
    }

}