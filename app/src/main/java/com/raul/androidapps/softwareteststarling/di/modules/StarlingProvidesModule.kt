package com.raul.androidapps.softwareteststarling.di.modules

import android.content.Context
import com.raul.androidapps.softwaretesttandem.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.StarlingApplication
import com.raul.androidapps.softwareteststarling.persistence.utils.DatabasePopulateTool
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class StarlingProvidesModule {


    @Provides
    fun providesContext(application: StarlingApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDb(
        context: Context,
        preferencesManager: PreferencesManager,
        databasePopulateTool: DatabasePopulateTool
    ): StarlingDatabase = StarlingDatabase.getInstance(context, preferencesManager, databasePopulateTool)


}