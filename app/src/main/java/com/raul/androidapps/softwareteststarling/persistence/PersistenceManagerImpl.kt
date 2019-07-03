package com.raul.androidapps.softwareteststarling.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.softwareteststarling.model.Direction
import com.raul.androidapps.softwareteststarling.model.SavingState
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.FeedsResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo
import com.raul.androidapps.softwareteststarling.security.Encryption
import com.raul.androidapps.softwareteststarling.utils.AppConstants
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
        db.accountDao().getDistinctAccountsWithAllInfo()

    /**
     * return all accounts
     * @return list of accounts
     */
    override fun getAccounts(): LiveData<List<AccountEntity>> =
        db.accountDao().getDistinctAccounts()

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

    /**
     * save account feeds in the db. The sensible information is stored encrypted
     * @param accountId account id
     * @param feedsResponse response fetched from the server
     */
    override suspend fun saveFeeds(accountId: String, feedsResponse: FeedsResponse?) {
        val listOfFeeds: MutableList<FeedsEntity> = mutableListOf()
        feedsResponse?.feedItems?.forEach {
            val feedStored = getFeed(it.feedItemUid)
            val availableForSaving = feedStored?.availableForSaving
                ?: if (it.direction == Direction.OUT.value && it.spendingCategory != AppConstants.SAVING_CATEGORY) {
                    SavingState.AVAILABLE.value
                } else {
                    SavingState.NOT_AVAILABLE.value
                }
            listOfFeeds.add(
                FeedsEntity.fromAccountFeedUnencrypted(
                    accountId,
                    it,
                    availableForSaving
                )
            )
        }
        db.feedsDao().insert(listOfFeeds)

    }

    /**
     * return a feed from the db
     * @param feedId feed id
     * @return the stored feed, null if not present
     */
    override suspend fun getFeed(feedId: String): FeedsEntity? =
        db.feedsDao().getFeed(feedId)

    /**
     * get list of Feeds with OUT direction and not sent to goal yet
     * @param accountId accountUid
     * @return list of feeds wrapped in an observable
     */
    override fun getPotentialSavings(accountId: String): LiveData<List<FeedsEntity>> =
        db.feedsDao().getPotentialSavings(accountId, Direction.OUT.value, SavingState.AVAILABLE.value)

    /**
     * mark Feeds as sent to goal not to get them in #getPotentialSavings more than once
     * @param feeds list of feeds
     */
    override suspend fun markFeedsAsSaved(feeds: List<String>?) {
        feeds?.let {
            db.feedsDao().markFeedsAsSaved(it, SavingState.SAVED.value)
        }
    }

}

