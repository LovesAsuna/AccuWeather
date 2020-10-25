package me.lovesasuna.accuweather.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.lovesasuna.accuweather.R
import me.lovesasuna.accuweather.bean.CityResponse.CityBean.AreaBean


/**
 * 区/县列表适配器
 *
 * @author LovesAsuna
 * @date 2020/10/25 13:10
 **/
class AreaAdapter(layoutResId: Int, data: List<AreaBean?>?) :
    BaseQuickAdapter<AreaBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: AreaBean) {
        helper.setText(R.id.tv_city, item.name) //区/县的名称
        helper.addOnClickListener(R.id.item_city) //点击事件 点击之后得到区/县  然后查询天气数据
    }
}