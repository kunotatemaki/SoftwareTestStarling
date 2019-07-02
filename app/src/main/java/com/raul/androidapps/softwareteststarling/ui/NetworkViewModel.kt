package com.raul.androidapps.softwareteststarling.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkViewModel @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    private val accounts: LiveData<List<AccountEntity>> = persistenceManager.getAccounts()

    fun getAccountsAsObservable() = accounts

    /**
     * This function request the accounts from the API, and stores the first one, if the call was successful, in the database.
     */
    fun getAccountsAsync() =
        viewModelScope.launch(Dispatchers.IO) {
            val accounts = networkServiceFactory.getServiceInstance().getAccounts()
            if (accounts.isSuccessful) {
                persistenceManager.saveAccounts(accounts.body())
            }
        }


    /**
     * This function request the account balance, and stores it in the database.
     * @param accountId id of the account to be fetched
     */
    fun getAccountBalanceAsync(accountId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val balanceResponse =
                networkServiceFactory.getServiceInstance().getAccountBalance(accountId)
            if (balanceResponse.isSuccessful) {
                persistenceManager.saveBalance(accountId, balanceResponse.body())
            }
        }

    /**
     * This function request the account identifiers, and stores it in the database.
     * @param accountId id of the account to be fetched
     */
    fun getAccountIdentifiersAsync(accountId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val identifiersResponse =
                networkServiceFactory.getServiceInstance().getAccountIdentifiers(accountId)
            if (identifiersResponse.isSuccessful) {
                persistenceManager.saveIdentifiers(accountId, identifiersResponse.body())
            }
        }


    /**
     * This function request the feeds for an account, and stores it in the database.
     * @param accountId id of the account to be fetched
     * @param categoryId id of the category for the feeds to be fetched
     */
    fun getAccountFeedsAsync(accountId: String, categoryId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val feedsResponse =
                networkServiceFactory.getServiceInstance().getFeeds(accountId, categoryId)
            if (feedsResponse.isSuccessful) {
                persistenceManager.saveFeeds(accountId, feedsResponse.body())
            }
        }


}