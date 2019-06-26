package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwareteststarling.persistence.entities.FooEntity


@Dao
abstract class FooDao : BaseDao<FooEntity>() {

    @Query("SELECT * FROM foo")
    abstract suspend fun getFoo(): List<FooEntity>


}