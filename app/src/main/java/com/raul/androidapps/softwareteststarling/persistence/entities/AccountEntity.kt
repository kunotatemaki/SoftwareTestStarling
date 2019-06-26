package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.model.Account
import com.raul.androidapps.softwareteststarling.security.Encryption
import java.util.*


@Entity(tableName = "account", indices = [(Index(value = arrayOf("account_uid"), unique = true))])
data class AccountEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "account_uid")
    val accountUid: String,
    @ColumnInfo(name = "default_category")
    var defaultCategory: String,
    @ColumnInfo(name = "currency")
    var currency: String,
    @ColumnInfo(name = "created_at")
    var createdAt: Date
) {
    companion object {
        @JvmStatic
        fun fromAccountUnencrypted(account: Account, encryption: Encryption): AccountEntity =
            AccountEntity(
                accountUid = encryption.encryptString(account.accountUid, BuildConfig.ENCRYPTION_ALIAS),
                defaultCategory = account.defaultCategory,
                currency = account.currency,
                createdAt = account.createdAt
            )
    }
    fun toAccountUnencrypted(encryption: Encryption): Account =
        Account(
            accountUid = encryption.decryptString(this.accountUid, BuildConfig.ENCRYPTION_ALIAS),
            defaultCategory = this.defaultCategory,
            currency = this.currency,
            createdAt = this.createdAt
        )
}

