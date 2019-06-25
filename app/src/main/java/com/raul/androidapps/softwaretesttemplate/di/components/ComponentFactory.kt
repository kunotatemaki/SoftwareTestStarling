package com.raul.androidapps.softwaretesttemplate.di.components

import com.raul.androidapps.softwaretesttemplate.TemplateApplication


object ComponentFactory {

    fun component(context: TemplateApplication): TemplateComponent {
        return DaggerTemplateComponent.builder()
                .application(context)
                .build()
    }

}