package com.raul.androidapps.softwareteststarling.model


enum class SavingState(val value: Int) {
    AVAILABLE(0), SAVED(1), NOT_AVAILABLE(2);

    companion object {
        private val map = SavingState.values().associateBy(SavingState::value)
        fun fromInt(type: Int): SavingState = map[type] ?: NOT_AVAILABLE
    }

}