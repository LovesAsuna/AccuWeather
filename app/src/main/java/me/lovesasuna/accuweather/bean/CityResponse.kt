package me.lovesasuna.accuweather.bean

/**
 * @author LovesAsuna
 * @date 2020/10/25 13:06
 **/
class CityResponse {
    /**
     * name : 北京市
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]}]
     */
    private var name: String? = null
    private var city: List<CityBean>? = null
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCity(): List<CityBean>? {
        return city
    }

    fun setCity(city: List<CityBean>?) {
        this.city = city
    }

    class CityBean {
        /**
         * name : 北京市
         * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
         */
        var name: String? = null
        private val area: List<AreaBean>? = null

        class AreaBean {
            /**
             * name : 北京市
             * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
             */
            var name: String? = null
        }
    }
}