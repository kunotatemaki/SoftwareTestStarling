package com.raul.androidapps.softwareteststarling.network

import com.raul.androidapps.softwareteststarling.model.Account
import com.raul.androidapps.softwareteststarling.model.AccountsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface StarlingApi {

//    @Headers(
//        "Accept: application/json",
//        "Authorization: Bearer bTSRyPCPzJATmJksA1WqwYEENAP6zL19VgWVVbkCX3pG2B4aWZRWccxtctn99SSY"
//    )
    @GET("/api/v2/accounts")
    suspend fun getAccounts(
//        @Header("Authorization") authorization: String
    ): Response<AccountsResponse>


}
