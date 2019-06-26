package com.raul.androidapps.softwareteststarling.network


interface NetworkServiceFactory {

    companion object {
        private const val BASE_URL = "https://api-sandbox.starlingbank.com"

    }

    fun getServiceInstance(): StarlingApi
}