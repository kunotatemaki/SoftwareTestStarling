package com.raul.androidapps.softwaretesttemplate.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class NetworkServiceFactory @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://base.url"

    }

    @Volatile
    private var instance: TemplateApi? = null

    fun getServiceInstance(): TemplateApi =
        instance ?: synchronized(this) {
            instance ?: buildNetworkService().also { instance = it }
        }

    private fun buildNetworkService(): TemplateApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TemplateApi::class.java)


}