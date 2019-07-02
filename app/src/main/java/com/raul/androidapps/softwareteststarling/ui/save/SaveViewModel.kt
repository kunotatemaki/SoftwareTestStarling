package com.raul.androidapps.softwareteststarling.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.androidapps.softwareteststarling.extensions.switchMap
import com.raul.androidapps.softwareteststarling.model.Goal
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import com.raul.androidapps.softwareteststarling.utils.AbsentLiveData
import com.raul.androidapps.softwareteststarling.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveViewModel @Inject constructor(
    private val persistenceManager: PersistenceManager,
    private val networkServiceFactory: NetworkServiceFactory,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()
    private val potentialSavingsObservable: LiveData<List<FeedsEntity>>

    init {
        potentialSavingsObservable = query.switchMap {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                persistenceManager.getPotentialSavings(it)
            }
        }
    }

    fun getPotentialSaving(): LiveData<List<FeedsEntity>> = potentialSavingsObservable

    fun getPotentialSavingsForAccount(accountUid: String?) {
        accountUid?.let {
            query.value = accountUid
        }
    }

    suspend fun getSavingsFromList(list: List<FeedsEntity>): Long =
        withContext(Dispatchers.Default) {
            list.mapNotNull { it.potentialSavings?.minorUnits }.sum()
        }

    fun sendToGoal(accountUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!preferencesManager.getBooleanFromPreferences(AppConstants.GOAL_ID)) {
                val goal = networkServiceFactory.getServiceInstance().createGoal(accountUid, Goal())
                if(goal.isSuccessful.not()) return@launch

            }
            val response =
                networkServiceFactory.getServiceInstance().saveToGoal()
            if (response.isSuccessful) {
                persistenceManager.markFeedsAsSaved(potentialSavingsObservable.value)
            }
        }
    }


}
