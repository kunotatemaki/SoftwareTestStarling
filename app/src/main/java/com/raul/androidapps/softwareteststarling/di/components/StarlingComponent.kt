package com.raul.androidapps.softwareteststarling.di.components

import com.raul.androidapps.softwareteststarling.StarlingApplication
import com.raul.androidapps.softwareteststarling.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (StarlingBindsModule::class),
        (StarlingProvidesModule::class), (FragmentsProvider::class), (FragmentsProvider::class), (ViewModelModule::class)]
)
interface StarlingComponent : AndroidInjector<StarlingApplication> {

    override fun inject(starlingApp: StarlingApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: StarlingApplication): Builder

        fun build(): StarlingComponent
    }

}