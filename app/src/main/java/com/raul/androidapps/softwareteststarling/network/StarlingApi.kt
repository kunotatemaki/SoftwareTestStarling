package com.raul.androidapps.softwareteststarling.network

import com.raul.androidapps.softwareteststarling.model.Goal
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.FeedsResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StarlingApi {

    @GET("/api/v2/accounts")
    suspend fun getAccounts(): Response<AccountsResponse>

    @GET("/api/v2/accounts/{account_uid}/balance")
    suspend fun getAccountBalance(@Path("account_uid") accountId: String): Response<BalanceResponse>

    @GET("/api/v2/accounts/{account_uid}/identifiers")
    suspend fun getAccountIdentifiers(@Path("account_uid") accountId: String): Response<IdentifiersResponse>

    @GET("/api/v2/feed/account/{account_uid}/category/{category_id}")
    suspend fun getFeeds(@Path("account_uid") accountId: String, @Path("category_id") categoryId: String): Response<FeedsResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goal")
    suspend fun createGoal(@Path("account_uid") accountId: String, @Body goal: Goal): Response<FeedsResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}")
    suspend fun saveToGoal(@Path("account_uid") accountId: String, @Path("savingsGoalUid") savingsGoalUid: String): Response<FeedsResponse>

}
