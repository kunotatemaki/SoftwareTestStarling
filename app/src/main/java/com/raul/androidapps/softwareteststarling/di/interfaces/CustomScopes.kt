package com.raul.androidapps.softwareteststarling.di.interfaces

import javax.inject.Scope


interface CustomScopes {

    @Scope
    annotation class ActivityScope

    @Scope
    annotation class FragmentScope

}