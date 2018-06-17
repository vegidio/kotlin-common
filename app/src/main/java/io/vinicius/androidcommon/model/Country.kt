package io.vinicius.androidcommon.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country
(
    @JsonProperty("area")
    val area: Int? = null,

    @JsonProperty("nativeName")
    val nativeName: String? = null,

    @JsonProperty("capital")
    val capital: String? = null,

    @JsonProperty("demonym")
    val demonym: String? = null,

    @JsonProperty("flag")
    val flag: String? = null,

    @JsonProperty("alpha2Code")
    val alpha2Code: String? = null,

    @JsonProperty("borders")
    val borders: List<String?>? = null,

    @JsonProperty("subregion")
    val subregion: String? = null,

    @JsonProperty("callingCodes")
    val callingCodes: List<String?>? = null,

    @JsonProperty("gini")
    val gini: Double? = null,

    @JsonProperty("population")
    val population: Int? = null,

    @JsonProperty("numericCode")
    val numericCode: String? = null,

    @JsonProperty("alpha3Code")
    val alpha3Code: String? = null,

    @JsonProperty("topLevelDomain")
    val topLevelDomain: List<String?>? = null,

    @JsonProperty("timezones")
    val timezones: List<String?>? = null,

    @JsonProperty("translations")
    val translations: Translations? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("altSpellings")
    val altSpellings: List<String?>? = null,

    @JsonProperty("region")
    val region: String? = null,

    @JsonProperty("latlng")
    val latlng: List<Double?>? = null
)