package com.raul.androidapps.softwaretesttemplate.di.modules

import android.content.Context
import com.raul.androidapps.softwaretesttandem.persistence.databases.TemplateDatabase
import com.raul.androidapps.softwaretesttemplate.TemplateApplication
import com.raul.androidapps.softwaretesttemplate.persistence.utils.DatabasePopulateTool
import com.raul.androidapps.softwaretesttemplate.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class TemplateProvidesModule {


    @Provides
    fun providesContext(application: TemplateApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDb(
        context: Context,
        preferencesManager: PreferencesManager,
        databasePopulateTool: DatabasePopulateTool
    ): TemplateDatabase = TemplateDatabase.getInstance(context, preferencesManager, databasePopulateTool)


}