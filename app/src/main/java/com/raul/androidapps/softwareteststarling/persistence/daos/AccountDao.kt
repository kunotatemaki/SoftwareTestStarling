package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.raul.androidapps.softwareteststarling.extensions.getDistinct
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo


@Dao
abstract class AccountDao : BaseDao<AccountEntity>() {

    @Transaction
    @Query("SELECT * FROM account")
    protected abstract fun getAccountsWithAllInfo(): LiveData<List<AccountWithAllInfo>>

    @Query("SELECT * FROM account")
    protected abstract fun getAccounts(): LiveData<List<AccountEntity>>

    fun getDistinctAccountsWithAllInfo(): LiveData<List<AccountWithAllInfo>> =
        getAccountsWithAllInfo().getDistinct()

    fun getDistinctAccounts(): LiveData<List<AccountEntity>> =
        getAccounts().getDistinct()

}