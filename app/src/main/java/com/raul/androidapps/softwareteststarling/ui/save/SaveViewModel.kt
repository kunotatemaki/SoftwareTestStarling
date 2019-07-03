package com.raul.androidapps.softwareteststarling.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raul.androidapps.softwareteststarling.extensions.getValueWithTwoDecimalsPrecisionInStringFormat
import com.raul.androidapps.softwareteststarling.extensions.switchMap
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.utils.AbsentLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveViewModel @Inject constructor(
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()
    private val potentialSavingsObservable: LiveData<List<FeedsEntity>>
    private var potentialSavingsAmount: Long = -1L

    init {
        potentialSavingsObservable = query.switchMap {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                persistenceManager.getPotentialSavings(it)
            }
        }
    }

    fun getPotentialSavingAsObservable(): LiveData<List<FeedsEntity>> = potentialSavingsObservable

    fun getPotentialSavingsForAccount(accountUid: String?) {
        accountUid?.let {
            query.value = accountUid
        }
    }

    fun getSavingsAmount(): Long = potentialSavingsAmount

    suspend fun getSavingsFromListAsString(list: List<FeedsEntity>, currency: String): String =
        withContext(Dispatchers.Default) {
            potentialSavingsAmount = list.mapNotNull { it.potentialSavings?.minorUnits }.sum()
            val amount = potentialSavingsAmount.toFloat() / 100
            "${amount.getValueWithTwoDecimalsPrecisionInStringFormat()} $currency"
        }


}
