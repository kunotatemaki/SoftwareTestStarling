package com.raul.androidapps.softwareteststarling.network.responses

data class IdentifiersResponse constructor(
    val accountIdentifier: String,
    val bankIdentifier: String,
    val iban: String,
    val bic: String
)