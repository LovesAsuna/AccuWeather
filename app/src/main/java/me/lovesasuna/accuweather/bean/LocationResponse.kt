package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationResponse(

	@field:JsonProperty("AdministrativeArea")
	val administrativeArea: AdministrativeArea? = null,

	@field:JsonProperty("ParentCity")
	val parentCity: ParentCity? = null,

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("SupplementalAdminAreas")
	val supplementalAdminAreas: List<SupplementalAdminAreasItem?>? = null,

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

data class Country(

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("ID")
	val iD: String? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)

data class AdministrativeArea(

	@field:JsonProperty("CountryID")
	val countryID: String? = null,

	@field:JsonProperty("LocalizedType")
	val localizedType: String? = null,

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("Level")
	val level: Int? = null,

	@field:JsonProperty("ID")
	val iD: String? = null,

	@field:JsonProperty("EnglishType")
	val englishType: String? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)

data class TimeZone(

	@field:JsonProperty("NextOffsetChange")
	val nextOffsetChange: Any? = null,

	@field:JsonProperty("GmtOffset")
	val gmtOffset: Int? = null,

	@field:JsonProperty("Code")
	val code: String? = null,

	@field:JsonProperty("IsDaylightSaving")
	val isDaylightSaving: Boolean? = null,

	@field:JsonProperty("Name")
	val name: String? = null
)

data class SupplementalAdminAreasItem(

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("Level")
	val level: Int? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)

data class GeoPosition(

	@field:JsonProperty("Elevation")
	val elevation: Elevation? = null,

	@field:JsonProperty("Latitude")
	val latitude: Double? = null,

	@field:JsonProperty("Longitude")
	val longitude: Double? = null
)

data class ParentCity(

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("Key")
	val key: String? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)

data class Elevation(

	@field:JsonProperty("Metric")
	val metric: Metric? = null,

	@field:JsonProperty("Imperial")
	val imperial: Imperial? = null
)

data class Region(

	@field:JsonProperty("LocalizedName")
	val localizedName: String? = null,

	@field:JsonProperty("ID")
	val iD: String? = null,

	@field:JsonProperty("EnglishName")
	val englishName: String? = null
)
