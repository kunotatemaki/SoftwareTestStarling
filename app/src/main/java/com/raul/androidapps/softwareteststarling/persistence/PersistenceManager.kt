package com.raul.androidapps.softwareteststarling.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity

interface PersistenceManager {

    suspend fun saveAccounts(accountResponse: AccountsResponse?)
    fun getAccounts(): LiveData<List<AccountEntity>>
    suspend fun saveBalance(accountId: String, balance: BalanceResponse?)
    suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?)

}