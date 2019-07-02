package com.raul.androidapps.softwareteststarling.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo
import com.raul.androidapps.softwareteststarling.security.Encryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    persistenceManager: PersistenceManager
) : ViewModel() {

    val accounts: LiveData<List<AccountWithAllInfo>> = persistenceManager.getAccountsWithAllInfo()

    //the list of feeds will be stored in cache because it takes long time to get them from the db and un-encrypt them.
    //thus, when going back from the next screen or on screen rotation, the info will be on the screen immediately
    private val listOfFeeds: MutableLiveData<List<Feed>> = MutableLiveData()

    fun getListOfFeeds(): LiveData<List<Feed>> = listOfFeeds

    suspend fun getBalanceFromEntity(balance: BalanceEntity?): BalanceResponse? =
        withContext(Dispatchers.Default) {
            balance?.toAccountBalance()
        }

    suspend fun getIdentifiersFromEntity(
        identifiers: IdentifiersEntity?,
        encryption: Encryption
    ): IdentifiersResponse? =
        withContext(Dispatchers.Default) {
            identifiers?.toAccountIdentifierUnencrypted(encryption)
        }

    suspend fun getFeedsFromEntities(list: List<FeedsEntity>, encryption: Encryption) =
        withContext(Dispatchers.Default) {
            listOfFeeds.postValue(list.map { it.toAccountFeedUnencrypted(encryption) })
        }

}
