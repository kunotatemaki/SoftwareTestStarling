package com.raul.androidapps.softwareteststarling.model

import com.raul.androidapps.softwareteststarling.utils.AppConstants

data class Goal constructor(
    val name: String = AppConstants.DEFAULT_GOAL_NAME,
    val currency: String = "GBP",
    val target: Money = Money("GBP", 11223344),
    val base64EncodedPhoto: String = "string"
)