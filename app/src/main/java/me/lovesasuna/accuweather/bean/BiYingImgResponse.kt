package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

data class BiYingImgResponse(

	@field:JsonProperty("images")
	val images: List<ImagesItem?>? = null,

	@field:JsonProperty("tooltips")
	val tooltips: Tooltips? = null
)

data class Tooltips(

	@field:JsonProperty("next")
	val next: String? = null,

	@field:JsonProperty("walle")
	val walle: String? = null,

	@field:JsonProperty("walls")
	val walls: String? = null,

	@field:JsonProperty("previous")
	val previous: String? = null,

	@field:JsonProperty("loading")
	val loading: String? = null
)

data class ImagesItem(

	@field:JsonProperty("quiz")
	val quiz: String? = null,

	@field:JsonProperty("urlbase")
	val urlbase: String? = null,

	@field:JsonProperty("copyright")
	val copyright: String? = null,

	@field:JsonProperty("bot")
	val bot: Int? = null,

	@field:JsonProperty("hs")
	val hs: List<Any?>? = null,

	@field:JsonProperty("startdate")
	val startdate: String? = null,

	@field:JsonProperty("title")
	val title: String? = null,

	@field:JsonProperty("url")
	val url: String? = null,

	@field:JsonProperty("enddate")
	val enddate: String? = null,

	@field:JsonProperty("top")
	val top: Int? = null,

	@field:JsonProperty("fullstartdate")
	val fullstartdate: String? = null,

	@field:JsonProperty("wp")
	val wp: Boolean? = null,

	@field:JsonProperty("copyrightlink")
	val copyrightlink: String? = null,

	@field:JsonProperty("hsh")
	val hsh: String? = null,

	@field:JsonProperty("drk")
	val drk: Int? = null
)
