package com.raul.androidapps.softwareteststarling.network


interface NetworkServiceFactory {
    fun getServiceInstance(): StarlingApi
}