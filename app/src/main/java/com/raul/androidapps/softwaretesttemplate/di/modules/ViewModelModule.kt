package com.raul.androidapps.softwaretesttemplate.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul.androidapps.softwaretesttemplate.di.interfaces.ViewModelKey
import com.raul.androidapps.softwaretesttemplate.ui.common.TemplateViewModelFactory
import com.raul.androidapps.softwaretesttemplate.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: TemplateViewModelFactory): ViewModelProvider.Factory
}