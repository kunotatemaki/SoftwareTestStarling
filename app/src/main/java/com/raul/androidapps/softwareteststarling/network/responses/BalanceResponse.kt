package com.raul.androidapps.softwareteststarling.network.responses

import com.raul.androidapps.softwareteststarling.model.Balance

data class BalanceResponse constructor(
    val clearedBalance: Balance,
    val effectiveBalance: Balance,
    val pendingTransactions: Balance,
    val availableToSpend: Balance,
    val acceptedOverdraft: Balance,
    val amount: Balance
)