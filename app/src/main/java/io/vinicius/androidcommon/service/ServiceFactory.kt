package io.vinicius.androidcommon.service

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ServiceFactory @Inject constructor(private val context: Context)
{
    private val cacheSize = 10 * 1024 * 1024

    fun <T> create(clazz: Class<T>, time: Long? = null, unit: TimeUnit? = null): T
    {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d(it) })
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        // Add the request headers
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(getRequestHeaders())

        // Add the cache support
        if (time != null && unit != null) {
            client.cache(Cache(context.cacheDir, cacheSize.toLong()))
                    .addInterceptor(getCachePolicy(time, unit))
        }

        // Get the base URL from the service
        val baseUrl = clazz.getField("BASE_URL").get(null).toString()

        // Initialize Retrofit with the OkHttp client
        val retrofit = Retrofit.Builder().client(client.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

        return retrofit.create(clazz)
    }

    // Interceptors

    private fun getRequestHeaders(): Interceptor
    {
        return Interceptor {
            val request = it.request().newBuilder()
                    .header("X-Parse-Application-Id", "parse_demo")
                    .header("X-Parse-REST-API-Key", "undefined")
                    .build()

            it.proceed(request)
        }
    }

    private fun getCachePolicy(period: Long, unit: TimeUnit): Interceptor
    {
        val seconds = unit.toSeconds(period)

        return Interceptor {
            val request = it.request().newBuilder()
                    .header("Cache-Control", "public, max-stale=" + seconds)
                    .build()

            it.proceed(request)
        }
    }
}
