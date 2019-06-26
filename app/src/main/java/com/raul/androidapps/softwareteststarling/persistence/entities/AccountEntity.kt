package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
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
        /**
         * convert a POJO with info from the server in encrypted info to store in the db
         * @param account plain text info from the server
         * @param encryption class for encrypt/decrypt
         * @return encrypted info ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountUnencrypted(account: AccountsResponse.Account, encryption: Encryption): AccountEntity =
            AccountEntity(
                accountUid = encryption.encryptString(account.accountUid, BuildConfig.ENCRYPTION_ALIAS),
                defaultCategory = account.defaultCategory,
                currency = account.currency,
                createdAt = account.createdAt
            )
    }
    /**
     * decrypt info from the db and return it as a POJO
     * @param encryption class for encrypt/decrypt
     * @return POJO with plain text
     */
    fun toAccountUnencrypted(encryption: Encryption): AccountsResponse.Account =
        AccountsResponse.Account(
            accountUid = encryption.decryptString(this.accountUid, BuildConfig.ENCRYPTION_ALIAS),
            defaultCategory = this.defaultCategory,
            currency = this.currency,
            createdAt = this.createdAt
        )
}

