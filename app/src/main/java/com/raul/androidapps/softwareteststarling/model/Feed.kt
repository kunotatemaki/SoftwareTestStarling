package com.raul.androidapps.softwareteststarling.model

import java.util.*

data class Feed constructor(
    val feedItemUid: String,
    val categoryUid: String?,
    val amount: Money?,
    val sourceAmount: Money?,
    val direction: String?,
    val updatedAt: Date?,
    val transactionTime: Date?,
    val settlementTime: Date?,
    val source: String?,
    val status: String?,
    val counterPartyType: String?,
    val counterPartyUid: String?,
    val counterPartyName: String?,
    val counterPartySubEntityUid: String?,
    val counterPartySubEntityName: String?,
    val counterPartySubEntityIdentifier: String?,
    val counterPartySubEntitySubIdentifier: String?,
    val reference: String?,
    val country: String?,
    val spendingCategory: String?,
    var potentialSavings: Money?,
    var sentToGoal: Boolean
)