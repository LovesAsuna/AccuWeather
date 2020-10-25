package me.lovesasuna.accuweather.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.lovesasuna.accuweather.R
import me.lovesasuna.accuweather.bean.CityResponse.CityBean


/**
 * @author LovesAsuna
 * @date 2020/10/25 13:08
 **/
class CityAdapter(layoutResId: Int, data: MutableList<CityBean>?) :
    BaseQuickAdapter<CityBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: CityBean) {
        helper.setText(R.id.tv_city, item.name) //市名称
        helper.addOnClickListener(R.id.item_city) //点击事件  点击进入区/县列表
    }
}