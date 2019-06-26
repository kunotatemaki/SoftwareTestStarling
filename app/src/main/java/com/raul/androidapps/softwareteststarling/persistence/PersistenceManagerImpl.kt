package com.raul.androidapps.softwareteststarling.persistence

import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.entities.FooEntity
import com.raul.androidapps.softwaretesttandem.persistence.databases.StarlingDatabase
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(
    private val db: StarlingDatabase
) : PersistenceManager {

    override suspend fun saveAccounts(accountResponse: AccountsResponse?) {

    }

    override suspend fun saveBalance(accountId: String, balance: BalanceResponse?) {

    }

    override suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?) {

    }

}

