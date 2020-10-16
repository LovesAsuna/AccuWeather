package me.lovesasuna.accuweather.bean

import com.fasterxml.jackson.annotation.JsonProperty

class WeatherResponse : ArrayList<WeatherResponseNode>()

data class WeatherResponseNode(

    @field:JsonProperty("Wind")
    val wind: Wind? = null,

    @field:JsonProperty("Temperature")
    val temperature: Temperature? = null,

    @field:JsonProperty("Past24HourTemperatureDeparture")
    val past24HourTemperatureDeparture: Past24HourTemperatureDeparture? = null,

    @field:JsonProperty("PressureTendency")
    val pressureTendency: PressureTendency? = null,

    @field:JsonProperty("ObstructionsToVisibility")
    val obstructionsToVisibility: String? = null,

    @field:JsonProperty("Ceiling")
    val ceiling: Ceiling? = null,

    @field:JsonProperty("RealFeelTemperatureShade")
    val realFeelTemperatureShade: RealFeelTemperatureShade? = null,

    @field:JsonProperty("EpochTime")
    val epochTime: Int? = null,

    @field:JsonProperty("RealFeelTemperature")
    val realFeelTemperature: RealFeelTemperature? = null,

    @field:JsonProperty("PrecipitationType")
    val precipitationType: Any? = null,

    @field:JsonProperty("HasPrecipitation")
    val hasPrecipitation: Boolean? = null,

    @field:JsonProperty("RelativeHumidity")
    val relativeHumidity: Int? = null,

    @field:JsonProperty("PrecipitationSummary")
    val precipitationSummary: PrecipitationSummary? = null,

    @field:JsonProperty("TemperatureSummary")
    val temperatureSummary: TemperatureSummary? = null,

    @field:JsonProperty("LocalObservationDateTime")
    val localObservationDateTime: String? = null,

    @field:JsonProperty("UVIndexText")
    val uVIndexText: String? = null,

    @field:JsonProperty("WeatherText")
    val weatherText: String? = null,

    @field:JsonProperty("CloudCover")
    val cloudCover: Int? = null,

    @field:JsonProperty("LocalSource")
    val localSource: LocalSource? = null,

    @field:JsonProperty("WindGust")
    val windGust: WindGust? = null,

    @field:JsonProperty("UVIndex")
    val uVIndex: Int? = null,

    @field:JsonProperty("Precip1hr")
    val precip1hr: Precip1hr? = null,

    @field:JsonProperty("WeatherIcon")
    val weatherIcon: Int? = null,

    @field:JsonProperty("DewPoint")
    val dewPoint: DewPoint? = null,

    @field:JsonProperty("Pressure")
    val pressure: Pressure? = null,

    @field:JsonProperty("IsDayTime")
    val isDayTime: Boolean? = null,

    @field:JsonProperty("IndoorRelativeHumidity")
    val indoorRelativeHumidity: Int? = null,

    @field:JsonProperty("ApparentTemperature")
    val apparentTemperature: ApparentTemperature? = null,

    @field:JsonProperty("WetBulbTemperature")
    val wetBulbTemperature: WetBulbTemperature? = null,

    @field:JsonProperty("Visibility")
    val visibility: Visibility? = null,

    @field:JsonProperty("WindChillTemperature")
    val windChillTemperature: WindChillTemperature? = null,

    @field:JsonProperty("Link")
    val link: String? = null,

    @field:JsonProperty("MobileLink")
    val mobileLink: String? = null
)

data class Minimum(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null,

    @field:JsonProperty("UnitType")
    val unitType: Int? = null,

    @field:JsonProperty("Value")
    val value: Int? = null,

    @field:JsonProperty("Unit")
    val unit: String? = null
)

data class Precip1hr(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Past6HourRange(

    @field:JsonProperty("Minimum")
    val minimum: Minimum? = null,

    @field:JsonProperty("Maximum")
    val maximum: Maximum? = null
)

data class PastHour(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Speed(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class TemperatureSummary(

    @field:JsonProperty("Past6HourRange")
    val past6HourRange: Past6HourRange? = null,

    @field:JsonProperty("Past24HourRange")
    val past24HourRange: Past24HourRange? = null,

    @field:JsonProperty("Past12HourRange")
    val past12HourRange: Past12HourRange? = null
)

data class Past12Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Maximum(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null,

    @field:JsonProperty("UnitType")
    val unitType: Int? = null,

    @field:JsonProperty("Value")
    val value: Int? = null,

    @field:JsonProperty("Unit")
    val unit: String? = null
)

data class Pressure(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class WindChillTemperature(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Direction(

    @field:JsonProperty("English")
    val english: String? = null,

    @field:JsonProperty("Degrees")
    val degrees: Int? = null,

    @field:JsonProperty("Localized")
    val localized: String? = null
)

data class PrecipitationSummary(

    @field:JsonProperty("Past6Hours")
    val past6Hours: Past6Hours? = null,

    @field:JsonProperty("Precipitation")
    val precipitation: Precipitation? = null,

    @field:JsonProperty("Past9Hours")
    val past9Hours: Past9Hours? = null,

    @field:JsonProperty("Past3Hours")
    val past3Hours: Past3Hours? = null,

    @field:JsonProperty("PastHour")
    val pastHour: PastHour? = null,

    @field:JsonProperty("Past18Hours")
    val past18Hours: Past18Hours? = null,

    @field:JsonProperty("Past24Hours")
    val past24Hours: Past24Hours? = null,

    @field:JsonProperty("Past12Hours")
    val past12Hours: Past12Hours? = null
)

data class Metric(

    @field:JsonProperty("UnitType")
    val unitType: Int? = null,

    @field:JsonProperty("Value")
    val value: Double? = null,

    @field:JsonProperty("Unit")
    val unit: String? = null
)

data class Past3Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Ceiling(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Past12HourRange(

    @field:JsonProperty("Minimum")
    val minimum: Minimum? = null,

    @field:JsonProperty("Maximum")
    val maximum: Maximum? = null
)

data class Past6Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Past24Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class RealFeelTemperature(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class DewPoint(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Past18Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class WetBulbTemperature(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Visibility(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class PressureTendency(

    @field:JsonProperty("Code")
    val code: String? = null,

    @field:JsonProperty("LocalizedText")
    val localizedText: String? = null
)

data class Temperature(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null,

    @field:JsonProperty("Minimum")
    val minimum: Minimum? = null,

    @field:JsonProperty("Maximum")
    val maximum: Maximum? = null
)

data class WindGust(

    @field:JsonProperty("Speed")
    val speed: Speed? = null
)

data class Imperial(

    @field:JsonProperty("UnitType")
    val unitType: Int? = null,

    @field:JsonProperty("Value")
    val value: Double? = null,

    @field:JsonProperty("Unit")
    val unit: String? = null
)

data class Wind(

    @field:JsonProperty("Speed")
    val speed: Speed? = null,

    @field:JsonProperty("Direction")
    val direction: Direction? = null
)

data class Precipitation(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class ApparentTemperature(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class Past24HourRange(

    @field:JsonProperty("Minimum")
    val minimum: Minimum? = null,

    @field:JsonProperty("Maximum")
    val maximum: Maximum? = null
)

data class Past24HourTemperatureDeparture(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class LocalSource(

    @field:JsonProperty("WeatherCode")
    val weatherCode: String? = null,

    @field:JsonProperty("Id")
    val id: Int? = null,

    @field:JsonProperty("Name")
    val name: String? = null,

    )

data class Past9Hours(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)

data class RealFeelTemperatureShade(

    @field:JsonProperty("Metric")
    val metric: Metric? = null,

    @field:JsonProperty("Imperial")
    val imperial: Imperial? = null
)
