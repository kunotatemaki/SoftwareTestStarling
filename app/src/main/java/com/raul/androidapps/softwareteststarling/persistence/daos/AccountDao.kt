package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.FooEntity


@Dao
abstract class AccountDao : BaseDao<AccountEntity>() {

    @Query("SELECT * FROM account")
    abstract fun getAccounts(): LiveData<List<AccountEntity>>

}