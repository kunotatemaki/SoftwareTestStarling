package com.raul.androidapps.softwareteststarling.model

data class Goal constructor(
    val name: String = "Trip to Paris",
    val currency: String = "GBP",
    val target: Money = Money("GBP", 11223344),
    val base64EncodedPhoto: String = "string"
)