package com.raul.androidapps.softwareteststarling.model

import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.persistence.relations.AccountWithAllInfo
import com.raul.androidapps.softwareteststarling.security.Encryption
import java.util.*

data class Account constructor(
    val accountUid: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: Date,
    val accountIdentifier: String?,
    val bankIdentifier: String?,
    val iban: String?,
    val bic: String?
){

    companion object{
        fun fromAccountEncrypted(accountWithAllInfo: AccountWithAllInfo, encryption: Encryption): Account =
            Account(
                accountUid = encryption.decryptString(accountWithAllInfo.account.accountUid, BuildConfig.ENCRYPTION_ALIAS),
                defaultCategory = accountWithAllInfo.account.defaultCategory,
                currency = accountWithAllInfo.account.currency,
                createdAt = accountWithAllInfo.account.createdAt,
                accountIdentifier = encryption.decryptString(accountWithAllInfo.identifiers.firstOrNull()?.accountIdentifier, BuildConfig.ENCRYPTION_ALIAS),
                bankIdentifier = encryption.decryptString(accountWithAllInfo.identifiers.firstOrNull()?.bankIdentifier, BuildConfig.ENCRYPTION_ALIAS),
                iban = encryption.decryptString(accountWithAllInfo.identifiers.firstOrNull()?.iban, BuildConfig.ENCRYPTION_ALIAS),
                bic = encryption.decryptString(accountWithAllInfo.identifiers.firstOrNull()?.bic, BuildConfig.ENCRYPTION_ALIAS)
            )

    }
}