package com.raul.androidapps.softwareteststarling.di.components

import com.raul.androidapps.softwareteststarling.StarlingApplication


object ComponentFactory {

    fun component(context: StarlingApplication): StarlingComponent {
        return DaggerStarlingComponent.builder()
                .application(context)
                .build()
    }

}