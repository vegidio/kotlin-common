package io.vinicius.androidcommon.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Translations
(
    @field:JsonProperty("br")
    val br: String? = null,

    @field:JsonProperty("de")
    val de: String? = null,

    @field:JsonProperty("pt")
    val pt: String? = null,

    @field:JsonProperty("ja")
    val ja: String? = null,

    @field:JsonProperty("it")
    val it: String? = null,

    @field:JsonProperty("fr")
    val fr: String? = null,

    @field:JsonProperty("es")
    val es: String? = null
)
