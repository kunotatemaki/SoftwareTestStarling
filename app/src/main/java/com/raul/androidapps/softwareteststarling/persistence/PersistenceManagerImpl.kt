package com.raul.androidapps.softwareteststarling.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.security.Encryption
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(
    private val db: StarlingDatabase,
    private val encryption: Encryption
) : PersistenceManager {

    override suspend fun saveAccounts(accountResponse: AccountsResponse?) {
        accountResponse?.accounts?.let { listUnencrypted->
            val listOfAccounts = listUnencrypted.map { AccountEntity.fromAccountUnencrypted(it, encryption) }
            db.accountDao().insert(listOfAccounts)
        }
    }

    override fun getAccounts(): LiveData<List<AccountEntity>> =
        db.accountDao().getAccounts()

    override suspend fun saveBalance(accountId: String, balance: BalanceResponse?) {

    }

    override suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?) {

    }

}

