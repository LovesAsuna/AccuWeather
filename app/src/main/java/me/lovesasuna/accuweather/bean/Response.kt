package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

data class Response(

	@field:JsonProperty("fxLink")
	val fxLink: String? = null,

	@field:JsonProperty("code")
	val code: String? = null,

	@field:JsonProperty("refer")
	val refer: Refer? = null,

	@field:JsonProperty("now")
	val now: Now? = null,

	@field:JsonProperty("updateTime")
	val updateTime: String? = null
)

data class Refer(

	@field:JsonProperty("license")
	val license: List<String?>? = null,

	@field:JsonProperty("sources")
	val sources: List<String?>? = null
)

data class Now(

	@field:JsonProperty("vis")
	val vis: String? = null,

	@field:JsonProperty("temp")
	val temp: String? = null,

	@field:JsonProperty("obsTime")
	val obsTime: String? = null,

	@field:JsonProperty("icon")
	val icon: String? = null,

	@field:JsonProperty("wind360")
	val wind360: String? = null,

	@field:JsonProperty("windDir")
	val windDir: String? = null,

	@field:JsonProperty("pressure")
	val pressure: String? = null,

	@field:JsonProperty("feelsLike")
	val feelsLike: String? = null,

	@field:JsonProperty("cloud")
	val cloud: String? = null,

	@field:JsonProperty("precip")
	val precip: String? = null,

	@field:JsonProperty("dew")
	val dew: String? = null,

	@field:JsonProperty("humidity")
	val humidity: String? = null,

	@field:JsonProperty("text")
	val text: String? = null,

	@field:JsonProperty("windSpeed")
	val windSpeed: String? = null,

	@field:JsonProperty("windScale")
	val windScale: String? = null
)
