package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

class LifeStyleResponse : ArrayList<LifeStyleItem>()

data class LifeStyleItem(

	@field:JsonProperty("LocalDateTime")
	val localDateTime: String? = null,

	@field:JsonProperty("Category")
	val category: String? = null,

	@field:JsonProperty("EpochDateTime")
	val epochDateTime: Int? = null,

	@field:JsonProperty("Value")
	val value: Double? = null,

	@field:JsonProperty("CategoryValue")
	val categoryValue: Int? = null,

	@field:JsonProperty("Text")
	val text: String? = null,

	@field:JsonProperty("ID")
	val iD: Int? = null,

	@field:JsonProperty("Ascending")
	val ascending: Boolean? = null,

	@field:JsonProperty("Link")
	val link: Any? = null,

	@field:JsonProperty("Name")
	val name: String? = null,

	@field:JsonProperty("MobileLink")
	val mobileLink: Any? = null
)
