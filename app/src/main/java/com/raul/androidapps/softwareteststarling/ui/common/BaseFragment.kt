package com.raul.androidapps.softwareteststarling.ui.common

import com.raul.androidapps.softwareteststarling.databinding.StarlingBindingComponent
import com.raul.androidapps.softwareteststarling.security.Encryption
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: StarlingViewModelFactory

    @Inject
    protected lateinit var starlingBindingComponent: StarlingBindingComponent

    @Inject
    protected lateinit var encryption: Encryption

}