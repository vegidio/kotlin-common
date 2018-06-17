package io.vinicius.androidcommon.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Translations
(
    @JsonProperty("br")
    val br: String? = null,

    @JsonProperty("de")
    val de: String? = null,

    @JsonProperty("pt")
    val pt: String? = null,

    @JsonProperty("ja")
    val ja: String? = null,

    @JsonProperty("it")
    val it: String? = null,

    @JsonProperty("fr")
    val fr: String? = null,

    @JsonProperty("es")
    val es: String? = null
)
