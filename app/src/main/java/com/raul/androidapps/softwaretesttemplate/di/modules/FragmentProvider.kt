package com.raul.androidapps.softwaretesttemplate.di.modules

import com.raul.androidapps.softwaretesttemplate.di.interfaces.CustomScopes
import com.raul.androidapps.softwaretesttemplate.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesMainFragmentFactory(): MainFragment

}