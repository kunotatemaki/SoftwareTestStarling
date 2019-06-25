package com.raul.androidapps.softwaretesttemplate.persistence.utils

import com.raul.androidapps.softwaretesttemplate.persistence.daos.FooDao
import com.raul.androidapps.softwaretesttemplate.preferences.PreferencesManager
import com.raul.androidapps.softwaretesttemplate.utils.AssetFileUtil
import javax.inject.Inject

class DatabasePopulateTool @Inject constructor(
    private val assetFileUtil: AssetFileUtil,
    private val preferencesManager: PreferencesManager
) {

    suspend fun populateDb(dao: FooDao) {

    }

}