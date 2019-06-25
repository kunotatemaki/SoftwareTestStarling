package com.raul.androidapps.softwaretesttemplate.persistence.daos

import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwaretesttemplate.persistence.entities.FooEntity
import com.raul.androidapps.softwaretesttemplate.persistence.daos.BaseDao


@Dao
abstract class FooDao : BaseDao<FooEntity>() {

    @Query("SELECT * FROM foo")
    abstract suspend fun getFoo(): List<FooEntity>


}