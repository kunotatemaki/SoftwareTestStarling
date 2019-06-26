package com.raul.androidapps.softwareteststarling.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.network.Resource
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkViewModel @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    private val networkStatus: MutableLiveData<Resource.Status> = MutableLiveData()

    fun getNetworkStatus(): LiveData<Resource.Status> = networkStatus

    /**
     * This function request the accounts from the API, and stores the first one, if the call was successful, in the database.
     * It only stores the first one because for the purpose of the app, it only manage the case of the user having one account.
     * In a real app, it should handle several accounts
     */
    suspend fun getAccounts() {
        networkStatus.value = Resource.Status.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            val accounts = networkServiceFactory.getServiceInstance().getAccounts()
            networkStatus.postValue(
                if (accounts.isSuccessful) {
                    accounts.body()?.accounts?.firstOrNull()?.let { account ->
                        persistenceManager.saveAccount(account)
                    }
                    Resource.Status.SUCCESS
                } else {
                    Resource.Status.ERROR
                }
            )
        }
    }
}