package com.raul.androidapps.softwareteststarling.di.modules

import com.raul.androidapps.softwareteststarling.resources.ResourcesManager
import com.raul.androidapps.softwareteststarling.resources.ResourcesManagerImpl
import dagger.Binds
import dagger.Module


@Module(includes = [(ViewModelModule::class)])
abstract class StarlingBindsModule {


    @Binds
    abstract fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

}