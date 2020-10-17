package me.lovesasuna.accuweather.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.lovesasuna.accuweather.R
import me.lovesasuna.accuweather.bean.DailyForecastsItem

/**
 * @author LovesAsuna
 * @date 2020/10/16 22:26
 *
 *
 * 天气预报列表展示适配器
 */
class WeatherForecastAdapter(
    layoutResId: Int,
    data: List<DailyForecastsItem>
) : BaseQuickAdapter<DailyForecastsItem, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: DailyForecastsItem) {
        helper.setText(R.id.tv_date, item.date) //日期
            .setText(R.id.tv_info, item.day?.iconPhrase) //天气
            .setText(R.id.tv_low_and_height, "${item.temperature?.minimum?.value}/${item.temperature?.maximum?.value}℃") //最低温和最高温
    }

}