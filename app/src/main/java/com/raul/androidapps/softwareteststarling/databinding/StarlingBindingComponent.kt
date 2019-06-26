package com.raul.androidapps.softwareteststarling.databinding

import androidx.databinding.DataBindingComponent
import javax.inject.Inject

class StarlingBindingComponent @Inject constructor(private val starlingBindingAdapters: StarlingBindingAdapters) : DataBindingComponent {
    override fun getStarlingBindingAdapters(): StarlingBindingAdapters {
        return starlingBindingAdapters
    }
}