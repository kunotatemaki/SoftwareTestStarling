package com.raul.androidapps.softwareteststarling.model

import com.raul.androidapps.softwareteststarling.persistence.utils.DateUtils


data class TransferBody constructor(
    val recurrenceRule: RecurrenceRule = RecurrenceRule(),
    val amount: Money
    ) {

    constructor(currency: String, amount: Long): this(recurrenceRule= RecurrenceRule(), amount = Money(currency = currency, minorUnits = amount))

    data class RecurrenceRule constructor(
        val startDate: String = DateUtils.getTodayDateForRecurrenceRule(),
        val frequency: String = "WEEKLY"
    )
}