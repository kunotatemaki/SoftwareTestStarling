package com.raul.androidapps.softwareteststarling.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo
import com.raul.androidapps.softwareteststarling.security.Encryption
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(
    private val db: StarlingDatabase,
    private val encryption: Encryption
) : PersistenceManager {

    /**
     * save accounts in the db.
     * @param accountResponse response fetched from the server
     */
    override suspend fun saveAccounts(accountResponse: AccountsResponse?) {
        accountResponse?.accounts?.let { listOfAccounts ->
            val listOfEntities =
                listOfAccounts.map { AccountEntity.fromAccountResponse(it) }
            db.accountDao().insert(listOfEntities)
        }
    }

    /**
     * return all accounts joining tables
     * @return list of accounts
     */
    override fun getAccountsWithAllInfo(): LiveData<List<AccountWithAllInfo>> =
        db.accountDao().getAccountsWithAllInfo()

    /**
     * return all accounts
     * @return list of accounts
     */
    override fun getAccounts(): LiveData<List<AccountEntity>> =
        db.accountDao().getAccounts()

    /**
     * save account balance in the db. The sensible information is stored encrypted
     * @param accountId account id
     * @param balance response fetched from the server
     */
    override suspend fun saveBalance(accountId: String, balance: BalanceResponse?) {
        val balanceEntity = BalanceEntity.fromAccountBalance(
            accountId,
            balance
        )
        db.balanceDao().insert(balanceEntity)
    }

    /**
     * save account identifiers in the db. The sensible information is stored encrypted
     * @param accountId account id
     * @param identifiersResponse response fetched from the server
     */
    override suspend fun saveIdentifiers(
        accountId: String,
        identifiersResponse: IdentifiersResponse?
    ) {
        val identifiers = IdentifiersEntity.fromAccountIdentifierUnencrypted(
            accountId,
            identifiersResponse,
            encryption
        )
        db.identifiersDao().insert(identifiers)
    }

}

