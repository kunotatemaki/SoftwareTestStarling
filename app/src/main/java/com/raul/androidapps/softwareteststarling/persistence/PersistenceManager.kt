package com.raul.androidapps.softwareteststarling.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.FeedsResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo

interface PersistenceManager {

    suspend fun saveAccounts(accountResponse: AccountsResponse?)
    fun getAccountsWithAllInfo(): LiveData<List<AccountWithAllInfo>>
    fun getAccounts(): LiveData<List<AccountEntity>>
    suspend fun saveBalance(accountId: String, balance: BalanceResponse?)
    suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?)
    suspend fun saveFeeds(accountId: String, feedsResponse: FeedsResponse?)
    suspend fun getFeed(feedId: String): FeedsEntity?
    fun getPotentialSavings(accountId: String): LiveData<List<FeedsEntity>>
    suspend fun markFeedsAsSaved(feeds: List<FeedsEntity>?)

}