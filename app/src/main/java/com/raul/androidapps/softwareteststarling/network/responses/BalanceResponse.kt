package com.raul.androidapps.softwareteststarling.network.responses

import com.raul.androidapps.softwareteststarling.model.Money

data class BalanceResponse constructor(
    val clearedBalance: Money?,
    val effectiveBalance: Money?,
    val pendingTransactions: Money?,
    val availableToSpend: Money?,
    val acceptedOverdraft: Money?,
    val amount: Money?
)