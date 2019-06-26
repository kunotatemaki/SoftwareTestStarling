package com.raul.androidapps.softwareteststarling.di.modules

import com.raul.androidapps.softwareteststarling.di.interfaces.CustomScopes
import com.raul.androidapps.softwareteststarling.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesMainFragmentFactory(): MainFragment

}