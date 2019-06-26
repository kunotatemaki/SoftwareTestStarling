package com.raul.androidapps.softwareteststarling.network.responses

import java.util.*

data class AccountsResponse constructor(
    val accounts: List<Account>
) {

    data class Account constructor(
        val accountUid: String,
        val defaultCategory: String,
        val currency: String,
        val createdAt: Date
    )
}
