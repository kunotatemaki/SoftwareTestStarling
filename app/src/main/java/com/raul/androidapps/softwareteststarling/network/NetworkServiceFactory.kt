package com.raul.androidapps.softwareteststarling.network

import com.google.gson.GsonBuilder
import com.raul.androidapps.softwareteststarling.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class NetworkServiceFactory @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://api-sandbox.starlingbank.com"

    }

    @Volatile
    private var instance: StarlingApi? = null

    fun getServiceInstance(): StarlingApi =
        instance ?: synchronized(this) {
            instance ?: buildNetworkService().also { instance = it }
        }

    private fun buildNetworkService(): StarlingApi {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.USER_ACCESS_TOKEN}")
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build().create(StarlingApi::class.java)
    }

}