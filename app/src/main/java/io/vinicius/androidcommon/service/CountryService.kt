package io.vinicius.androidcommon.service

import io.reactivex.Observable
import io.vinicius.androidcommon.model.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService
{
    companion object {
        internal const val BASE_URL = "https://restcountries.eu/rest/v1/"
    }

    @GET("all")
    fun getAll(): Observable<List<Country>>

    @GET("lang/{language}")
    fun getByCallingCode(
            @Path("language") language: String
    ): Observable<List<Country>>

    @GET("alpha/{countryCode}")
    fun getByCountryCode(
            @Path("countryCode") countryCode: String
    ): Observable<Country>
}