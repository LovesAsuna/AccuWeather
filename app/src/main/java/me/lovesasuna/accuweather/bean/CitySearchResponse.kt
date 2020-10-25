package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

class CitySearchResponse : ArrayList<CitySearchItem>()

data class CitySearchItem(

	@field:JsonProperty("AdministrativeArea")
	val administrativeArea: AdministrativeArea? = null,

	@field:JsonProperty("ParentCity")
	val parentCity: ParentCity? = null,

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("SupplementalAdminAreas")
	val supplementalAdminAreas: List<Any?>? = null,

	@field:JsonProperty("DataSets")
	val dataSets: List<String?>? = null,

	@field:JsonProperty("Rank")
	val rank: Int? = null,

	@field:JsonProperty("IsAlias")
	val isAlias: Boolean? = null,

	@field:JsonProperty("Type")
	val type: String? = null,

	@field:JsonProperty("TimeZone")
	val timeZone: TimeZone? = null,

	@field:JsonProperty("Version")
	val version: Int? = null,

	@field:JsonProperty("PrimaryPostalCode")
	val primaryPostalCode: String? = null,

	@field:JsonProperty("Region")
	val region: Region? = null,

	@field:JsonProperty("Country")
	val country: Country? = null,

	@field:JsonProperty("GeoPosition")
	val geoPosition: GeoPosition? = null,

	@field:JsonProperty("Key")
	val key: String? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)
