package com.raul.androidapps.softwareteststarling.network

import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.FeedsResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StarlingApi {

    @GET("/api/v2/accounts")
    suspend fun getAccounts(): Response<AccountsResponse>

    @GET("/api/v2/accounts/{account_id}/balance")
    suspend fun getAccountBalance(@Path("account_id") accountId: String): Response<BalanceResponse>

    @GET("/api/v2/accounts/{account_id}/identifiers")
    suspend fun getAccountIdentifiers(@Path("account_id") accountId: String): Response<IdentifiersResponse>

    @GET("/api/v2/feed/account/{account_id}/category/{category_id}")
    suspend fun getFeeds(@Path("account_id") accountId: String, @Path("category_id") categoryId: String): Response<FeedsResponse>

}
