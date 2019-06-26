package com.raul.androidapps.softwareteststarling.di.modules

import com.raul.androidapps.softwareteststarling.di.interfaces.CustomScopes
import com.raul.androidapps.softwareteststarling.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}