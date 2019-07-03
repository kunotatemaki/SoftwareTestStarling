package com.raul.androidapps.softwareteststarling.network

import com.raul.androidapps.softwareteststarling.model.Goal
import com.raul.androidapps.softwareteststarling.model.SavingBody
import com.raul.androidapps.softwareteststarling.model.TransferBody
import com.raul.androidapps.softwareteststarling.network.responses.*
import retrofit2.Response
import retrofit2.http.*

interface StarlingApi {

    @GET("/api/v2/accounts")
    suspend fun getAccounts(): Response<AccountsResponse>

    @GET("/api/v2/accounts/{account_uid}/balance")
    suspend fun getAccountBalance(@Path("account_uid") accountUid: String): Response<BalanceResponse>

    @GET("/api/v2/accounts/{account_uid}/identifiers")
    suspend fun getAccountIdentifiers(@Path("account_uid") accountUid: String): Response<IdentifiersResponse>

    @GET("/api/v2/feed/account/{account_uid}/category/{category_id}")
    suspend fun getFeeds(@Path("account_uid") accountUid: String, @Path("category_id") categoryId: String): Response<FeedsResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goals")
    suspend fun createGoal(@Path("account_uid") accountUid: String, @Body goal: Goal): Response<CreateGoalResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}")
    suspend fun saveToGoal(
        @Path("account_uid") accountUid: String,
        @Path("savingsGoalUid") savingsGoalUid: String,
        @Path("transferUid") transferUid: String,
        @Body money: SavingBody
    ): Response<SaveToGoalResponse>

    @PUT("/api/v2/account/{account_uid}/savings-goals/{savingsGoalUid}/recurring-transfer")
    suspend fun createTransfer(
        @Path("account_uid") accountUid: String,
        @Path("savingsGoalUid") savingsGoalUid: String,
        @Body transferBody: TransferBody
    ): Response<CreateTransferResponse>

    @DELETE("/api/v2/account/{account_uid}/savings-goals/{savingsGoalUid}/recurring-transfer")
    suspend fun deleteTransfer(
        @Path("account_uid") accountUid: String,
        @Path("savingsGoalUid") savingsGoalUid: String
    ): Response<Void>

}
