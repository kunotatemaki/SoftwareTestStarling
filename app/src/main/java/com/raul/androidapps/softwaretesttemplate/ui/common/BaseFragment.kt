package com.raul.androidapps.softwaretesttemplate.ui.common

import com.raul.androidapps.softwaretesttemplate.databinding.TemplateBindingComponent
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: TemplateViewModelFactory

    @Inject
    protected lateinit var templateBindingComponent: TemplateBindingComponent

}