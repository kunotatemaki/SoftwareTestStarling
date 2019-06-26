package com.raul.androidapps.softwareteststarling.persistence

import com.raul.androidapps.softwareteststarling.persistence.entities.FooEntity

interface PersistenceManager {

    suspend fun getFoo(name: String): List<FooEntity>

}