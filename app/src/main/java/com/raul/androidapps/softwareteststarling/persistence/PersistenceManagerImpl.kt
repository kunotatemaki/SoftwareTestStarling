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

    /**
     * save accounts in the db. The accounts have the accountUid encrypted
     * @param accountResponse response fetched from the server
     */
    override suspend fun saveAccounts(accountResponse: AccountsResponse?) {
        accountResponse?.accounts?.let { listUnencrypted->
            val listOfAccounts = listUnencrypted.map { AccountEntity.fromAccountUnencrypted(it, encryption) }
            db.accountDao().insert(listOfAccounts)
        }
    }

    /**
     * return the stored accounts in an observable
     * @return list of accounts wrapped in a LiveData
     */
    override fun getAccounts(): LiveData<List<AccountEntity>> =
        db.accountDao().getAccounts()

    /**
     * save account balance in the db. The sensible information is stored encrypted
     * @param accountId account id
     * @param balance response fetched from the server
     */
    override suspend fun saveBalance(accountId: String, balanceResponse: BalanceResponse?) {

    }

    /**
     * save account identifiers in the db. The sensible information is stored encrypted
     * @param accountId account id
     * @param balance response fetched from the server
     */
    override suspend fun saveIdentifiers(accountId: String, identifiersResponse: IdentifiersResponse?) {

    }

}

