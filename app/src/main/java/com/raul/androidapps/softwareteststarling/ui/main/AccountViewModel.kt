package com.raul.androidapps.softwareteststarling.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    persistenceManager: PersistenceManager
) : ViewModel() {

    val accounts: LiveData<List<AccountWithAllInfo>> = persistenceManager.getAccountsWithAllInfo()
}
