package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

data class ForecastResponse(

	@field:JsonProperty("Headline")
	val headline: Headline? = null,

	@field:JsonProperty("DailyForecasts")
	val dailyForecasts: List<DailyForecastsItem>? = null
)

data class Night(

	@field:JsonProperty("HasPrecipitation")
	val hasPrecipitation: Boolean? = null,

	@field:JsonProperty("IconPhrase")
	val iconPhrase: String? = null,

	@field:JsonProperty("LocalSource")
	val localSource: LocalSource? = null,

	@field:JsonProperty("Icon")
	val icon: Int? = null,

	@field:JsonProperty("PrecipitationType")
	val precipitationType: String? = null,

	@field:JsonProperty("PrecipitationIntensity")
	val precipitationIntensity: String? = null
)

data class DailyForecastsItem(

	@field:JsonProperty("Temperature")
	val temperature: Temperature? = null,

	@field:JsonProperty("Night")
	val night: Night? = null,

	@field:JsonProperty("EpochDate")
	val epochDate: Int? = null,

	@field:JsonProperty("Day")
	val day: Day? = null,

	@field:JsonProperty("Sources")
	val sources: List<String?>? = null,

	@field:JsonProperty("Date")
	var date: String? = null,

	@field:JsonProperty("Link")
	val link: String? = null,

	@field:JsonProperty("MobileLink")
	val mobileLink: String? = null
)

data class Headline(

	@field:JsonProperty("Category")
	val category: String? = null,

	@field:JsonProperty("EndEpochDate")
	val endEpochDate: Int? = null,

	@field:JsonProperty("EffectiveEpochDate")
	val effectiveEpochDate: Int? = null,

	@field:JsonProperty("Severity")
	val severity: Int? = null,

	@field:JsonProperty("Text")
	val text: String? = null,

	@field:JsonProperty("EndDate")
	val endDate: String? = null,

	@field:JsonProperty("Link")
	val link: String? = null,

	@field:JsonProperty("EffectiveDate")
	val effectiveDate: String? = null,

	@field:JsonProperty("MobileLink")
	val mobileLink: String? = null
)

data class Day(

	@field:JsonProperty("HasPrecipitation")
	val hasPrecipitation: Boolean? = null,

	@field:JsonProperty("IconPhrase")
	val iconPhrase: String? = null,

	@field:JsonProperty("LocalSource")
	val localSource: LocalSource? = null,

	@field:JsonProperty("Icon")
	val icon: Int? = null,

	@field:JsonProperty("PrecipitationType")
	val precipitationType: String? = null,

	@field:JsonProperty("PrecipitationIntensity")
	val precipitationIntensity: String? = null
)
