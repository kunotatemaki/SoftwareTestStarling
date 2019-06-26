package com.raul.androidapps.softwareteststarling.persistence.utils

import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import com.raul.androidapps.softwareteststarling.utils.AssetFileUtil
import javax.inject.Inject

class DatabasePopulateTool @Inject constructor(
    private val assetFileUtil: AssetFileUtil,
    private val preferencesManager: PreferencesManager
) {

    suspend fun populateDb() {

    }

}