package com.raul.androidapps.softwaretesttemplate.di.components

import com.raul.androidapps.softwaretesttemplate.TemplateApplication
import com.raul.androidapps.softwaretesttemplate.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (TemplateBindsModule::class),
        (TemplateProvidesModule::class), (FragmentsProvider::class), (FragmentsProvider::class), (ViewModelModule::class)]
)
interface TemplateComponent : AndroidInjector<TemplateApplication> {

    override fun inject(templateApp: TemplateApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TemplateApplication): Builder

        fun build(): TemplateComponent
    }

}