package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.*
import com.raul.androidapps.softwareteststarling.model.Money
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse


@Entity(
    tableName = "balance",
    indices = [(Index(value = arrayOf("account_uid"), unique = true))]
)
data class BalanceEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "account_uid")
    val accountUid: String,
    @Embedded(prefix = "cleared_balance")
    val clearedBalance: Money?,
    @Embedded(prefix = "effective_balance")
    val effectiveBalance: Money?,
    @Embedded(prefix = "pending_transactions")
    val pendingTransactions: Money?,
    @Embedded(prefix = "available_to_spend")
    val availableToSpend: Money?,
    @Embedded(prefix = "accepted_overdraft")
    val acceptedOverdraft: Money?,
    @Embedded(prefix = "amount")
    val amount: Money?
) {
    companion object {
        /**
         * convert a POJO with info from the server in an entity to be stored in the db
         * @param accountUid account uid to build the relationship between tables
         * @param balanceResponse info from the server
         * @return entity ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountBalance(
            accountUid: String,
            balanceResponse: BalanceResponse?
        ): BalanceEntity =
            BalanceEntity(
                accountUid = accountUid,
                clearedBalance = balanceResponse?.clearedBalance,
                effectiveBalance = balanceResponse?.effectiveBalance,
                pendingTransactions = balanceResponse?.pendingTransactions,
                availableToSpend = balanceResponse?.availableToSpend,
                acceptedOverdraft = balanceResponse?.acceptedOverdraft,
                amount = balanceResponse?.amount
            )
    }

    /**
     * decrypt info from the db and return it as a POJO
     * @return POJO with plain text
     */
    fun toAccountBalance(): BalanceResponse =
        BalanceResponse(
            clearedBalance = this.clearedBalance,
            effectiveBalance = this.effectiveBalance,
            pendingTransactions = this.pendingTransactions,
            availableToSpend = this.availableToSpend,
            acceptedOverdraft = this.acceptedOverdraft,
            amount = this.amount
        )
}

