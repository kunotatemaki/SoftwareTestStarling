package com.raul.androidapps.softwareteststarling.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.model.Goal
import com.raul.androidapps.softwareteststarling.model.Money
import com.raul.androidapps.softwareteststarling.model.SavingBody
import com.raul.androidapps.softwareteststarling.model.TransferBody
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import com.raul.androidapps.softwareteststarling.resources.ResourcesManager
import com.raul.androidapps.softwareteststarling.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NetworkViewModel @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager,
    private val preferencesManager: PreferencesManager,
    private val resourcesManager: ResourcesManager
) : ViewModel() {

    private val accounts: LiveData<List<AccountEntity>> = persistenceManager.getAccounts()
    private val networkError: MutableLiveData<String> = MutableLiveData()

    fun getAccountsAsObservable() = accounts
    fun resetError() {
        networkError.value = null
    }

    fun getNetworkError(): LiveData<String> = networkError

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
     * @param accountUid id of the account to be fetched
     */
    fun getAccountBalanceAsync(accountUid: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val balanceResponse =
                networkServiceFactory.getServiceInstance().getAccountBalance(accountUid)
            if (balanceResponse.isSuccessful) {
                persistenceManager.saveBalance(accountUid, balanceResponse.body())
            }
        }

    /**
     * This function request the account identifiers, and stores it in the database.
     * @param accountUid id of the account to be fetched
     */
    fun getAccountIdentifiersAsync(accountUid: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val identifiersResponse =
                networkServiceFactory.getServiceInstance().getAccountIdentifiers(accountUid)
            if (identifiersResponse.isSuccessful) {
                persistenceManager.saveIdentifiers(accountUid, identifiersResponse.body())
            }
        }


    /**
     * This function request the feeds for an account, and stores it in the database.
     * @param accountUid id of the account to be fetched
     * @param categoryId id of the category for the feeds to be fetched
     */
    fun getAccountFeedsAsync(accountUid: String, categoryId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val feedsResponse =
                networkServiceFactory.getServiceInstance().getFeeds(accountUid, categoryId)
            if (feedsResponse.isSuccessful) {
                persistenceManager.saveFeeds(accountUid, feedsResponse.body())
            }
        }

    /**
     * This function send the money to the goal.
     * If the goal id is not stored inthe preferences, a new one is created automatically
     * Creates a Recurring transfer
     * Sends the money to the goal using the transfer id
     * Deletes the transfer
     * @param accountUid id of the account to be fetched
     * @param feedsIds list of ids of the feeds to get the money from
     * @param amount amount of money to be saved
     * @param currency currency code
     */
    fun sendToGoal(accountUid: String, feedsIds: List<String>, amount: Long, currency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var goalId: String = preferencesManager.getStringFromPreferences(AppConstants.GOAL_ID)
            if (goalId.isBlank()) {
                //create the default goal
                val goal = networkServiceFactory.getServiceInstance().createGoal(accountUid, Goal())
                if (goal.isSuccessful.not() || goal.body()?.success == false || goal.body()?.savingsGoalUid == null) {
                    networkError.postValue(resourcesManager.getString(R.string.error_sending_goal))
                    Timber.e("Error creating goal")
                    return@launch
                } else {
                    goalId = goal.body()?.savingsGoalUid ?: return@launch
                }
                preferencesManager.setStringIntoPreferences(AppConstants.GOAL_ID, goalId)
            }
            //create recurrent transfer
            val transfer =
                networkServiceFactory.getServiceInstance().createTransfer(accountUid, goalId, TransferBody(currency = currency, amount = amount))
            val transferId: String
            if (transfer.isSuccessful.not() || transfer.body()?.success == false) {
                networkError.postValue(resourcesManager.getString(R.string.error_sending_goal))
                Timber.e("Error creating transfer")
                return@launch
            }
            transferId = transfer.body()?.transferUid ?: return@launch
            //make the payment
            val payment =
                networkServiceFactory.getServiceInstance().saveToGoal(accountUid, goalId, transferId, SavingBody(amount = Money(currency = currency, minorUnits = amount)))
            if (payment.isSuccessful.not() || payment.body()?.success == false) {
                networkError.postValue(resourcesManager.getString(R.string.error_sending_goal))
                Timber.e("Error sending money")
            }else {
                //mark feeds as saved
                persistenceManager.markFeedsAsSaved(feedsIds)
            }

            //delete transfer
            val delete = networkServiceFactory.getServiceInstance().deleteTransfer(accountUid, goalId)
            if (delete.isSuccessful.not()) {
                Timber.e("Error deleting transfer")
            }
        }
    }


}