package me.lovesasuna.accuweather.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.lovesasuna.accuweather.R
import me.lovesasuna.accuweather.bean.CityResponse


/**
 * 省列表适配器
 *
 * @author LovesAsuna
 * @date 2020/10/25 13:07
 **/
class ProvinceAdapter(layoutResId: Int,  data: List<CityResponse?>?) :
    BaseQuickAdapter<CityResponse, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: CityResponse) {
        helper.setText(R.id.tv_city, item.getName()) //省名称
        helper.addOnClickListener(R.id.item_city) //点击之后进入市级列表
    }
}