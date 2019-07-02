package com.raul.androidapps.softwareteststarling.network.responses

import com.raul.androidapps.softwareteststarling.model.Account

data class AccountsResponse constructor(
    val accounts: List<Account>
)