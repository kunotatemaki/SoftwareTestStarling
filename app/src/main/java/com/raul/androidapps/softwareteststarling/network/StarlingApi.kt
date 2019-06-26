package com.raul.androidapps.softwareteststarling.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface StarlingApi {

    @GET("url")
    suspend fun foo(
        @Query("param1") param1: Long,
        @Query("param2") string: String
    ): Response<Objects>


}
