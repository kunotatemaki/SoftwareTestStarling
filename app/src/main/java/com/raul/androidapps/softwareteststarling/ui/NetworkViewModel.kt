package com.raul.androidapps.softwareteststarling.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NetworkViewModel @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    /**
     * This function request the accounts from the API, and stores the first one, if the call was successful, in the database.
     */
    fun getAccounts() =
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
    fun getAccountBalance(accountId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val balanceResponse = networkServiceFactory.getServiceInstance().getAccountBalance(accountId)
            if (balanceResponse.isSuccessful) {
                persistenceManager.saveBalance(accountId, balanceResponse.body())
            }
        }


    /**
     * This function request the account identifiers, and stores it in the database.
     * @param accountId id of the account to be fetched
     */
    fun getAccountIdentifiers(accountId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val identifiersResponse = networkServiceFactory.getServiceInstance().getAccountIdentifiers(accountId)
            if (identifiersResponse.isSuccessful) {
                persistenceManager.saveIdentifiers(accountId, identifiersResponse.body())
            }
        }


}