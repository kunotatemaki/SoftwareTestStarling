package com.raul.androidapps.softwaretesttemplate.databinding

import androidx.databinding.DataBindingComponent
import javax.inject.Inject

class TemplateBindingComponent @Inject constructor(private val templateBindingAdapters: TemplateBindingAdapters) : DataBindingComponent {
    override fun getTemplateBindingAdapters(): TemplateBindingAdapters {
        return templateBindingAdapters
    }
}