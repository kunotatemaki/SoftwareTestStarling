package com.raul.androidapps.softwaretesttemplate.di.modules

import com.raul.androidapps.softwaretesttemplate.di.interfaces.CustomScopes
import com.raul.androidapps.softwaretesttemplate.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}