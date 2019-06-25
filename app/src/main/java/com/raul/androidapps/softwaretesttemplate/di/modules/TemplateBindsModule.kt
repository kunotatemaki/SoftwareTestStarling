package com.raul.androidapps.softwaretesttemplate.di.modules

import com.raul.androidapps.softwaretesttemplate.resources.ResourcesManager
import com.raul.androidapps.softwaretesttemplate.resources.ResourcesManagerImpl
import dagger.Binds
import dagger.Module


@Module(includes = [(ViewModelModule::class)])
abstract class TemplateBindsModule {


    @Binds
    abstract fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

}