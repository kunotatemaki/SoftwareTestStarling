package com.raul.androidapps.softwareteststarling.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul.androidapps.softwareteststarling.di.interfaces.ViewModelKey
import com.raul.androidapps.softwareteststarling.ui.common.StarlingViewModelFactory
import com.raul.androidapps.softwareteststarling.ui.main.AccountsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountsViewModel::class)
    internal abstract fun bindMainViewModel(accountsViewModel: AccountsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: StarlingViewModelFactory): ViewModelProvider.Factory
}