package com.raul.androidapps.softwareteststarling.model

import java.util.*

data class Account constructor(
    val accountUid: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: Date
)