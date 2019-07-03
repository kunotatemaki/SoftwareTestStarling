package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.softwareteststarling.model.Account
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
    var createdAt: Date,
    @ColumnInfo(name = "last_fetched_at")
    var lastFetched: Date

) {
    companion object {
        /**
         * convert a POJO with info from the server into an entity to be stored in the db
         * @param account plain text info from the server
         * @return info ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountResponse(account: Account): AccountEntity =
            AccountEntity(
                accountUid = account.accountUid,
                defaultCategory = account.defaultCategory,
                currency = account.currency,
                createdAt = account.createdAt,
                lastFetched = Date()
            )
    }

    /**
     * pojo info from the entity
     * @return POJO with plain text
     */
    fun toAccountPojo(): Account =
        Account(
            accountUid = this.accountUid,
            defaultCategory = this.defaultCategory,
            currency = this.currency,
            createdAt = this.createdAt
        )
}

