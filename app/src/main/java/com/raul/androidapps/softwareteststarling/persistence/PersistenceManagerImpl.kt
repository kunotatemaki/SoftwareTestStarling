package com.raul.androidapps.softwareteststarling.persistence

import com.raul.androidapps.softwaretesttandem.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.persistence.entities.FooEntity
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(
    private val db: StarlingDatabase
) : PersistenceManager {

    override suspend fun getFoo(name: String): List<FooEntity> =
        db.fooDao().getFoo()

}

