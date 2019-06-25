package com.raul.androidapps.softwaretesttemplate.persistence

import com.raul.androidapps.softwaretesttandem.persistence.databases.TemplateDatabase
import com.raul.androidapps.softwaretesttemplate.persistence.entities.FooEntity
import javax.inject.Inject

class PersistenceManagerImpl @Inject constructor(
    private val db: TemplateDatabase
) : PersistenceManager {

    override suspend fun getFoo(name: String): List<FooEntity> =
        db.fooDao().getFoo()

}

