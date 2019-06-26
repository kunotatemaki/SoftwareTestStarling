package com.raul.androidapps.softwareteststarling.persistence

import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse

interface PersistenceManager {

    suspend fun saveAccounts(accountResponse: AccountsResponse?)
    suspend fun saveBalance(accountId: String, balance: BalanceResponse?)
    suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?)

}