package com.raul.androidapps.softwaretesttemplate.persistence

import com.raul.androidapps.softwaretesttemplate.persistence.entities.FooEntity

interface PersistenceManager {

    suspend fun getFoo(name: String): List<FooEntity>

}