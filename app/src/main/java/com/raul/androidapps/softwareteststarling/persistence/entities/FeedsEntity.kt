package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.*
import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.extensions.getPotentialSavings
import com.raul.androidapps.softwareteststarling.model.Direction
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.model.Money
import com.raul.androidapps.softwareteststarling.model.SavingState
import com.raul.androidapps.softwareteststarling.security.Encryption
import java.util.*


@Entity(
    tableName = "feeds",
    indices = [(Index(value = arrayOf("account_uid"), unique = false))]
)
data class FeedsEntity constructor(
    @ColumnInfo(name = "account_uid")
    val accountUid: String,
    @PrimaryKey
    @ColumnInfo(name = "feed_item_uid")
    val feedItemUid: String,
    @ColumnInfo(name = "category_uid")
    var categoryUid: String?,
    @Embedded(prefix = "amount_")
    var amount: Money?,
    @Embedded(prefix = "source_amount_")
    var sourceAmount: Money?,
    @ColumnInfo(name = "direction")
    var direction: String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date?,
    @ColumnInfo(name = "transaction_time")
    val transactionTime: Date?,
    @ColumnInfo(name = "settlement_time")
    val settlementTime: Date?,
    @ColumnInfo(name = "source")
    var source: String?,
    @ColumnInfo(name = "status")
    var status: String?,
    @ColumnInfo(name = "counter_party_type")
    var counterPartyType: String?,
    @ColumnInfo(name = "counter_party_uid")
    val counterPartyUid: String?,
    @ColumnInfo(name = "counter_party_name")
    var counterPartyName: String?,
    @ColumnInfo(name = "counter_party_sub_entity_uid")
    var counterPartySubEntityUid: String?,
    @ColumnInfo(name = "counter_party_sub_entity_name")
    var counterPartySubEntityName: String?,
    @ColumnInfo(name = "counter_party_sub_entity_identifier")
    var counterPartySubEntityIdentifier: String?,
    @ColumnInfo(name = "counter_party_sub_entity_sub_identifier")
    var counterPartySubEntitySubIdentifier: String?,
    @ColumnInfo(name = "reference")
    val reference: String?,
    @ColumnInfo(name = "country")
    var country: String?,
    @ColumnInfo(name = "spending_category")
    var spendingCategory: String?,
    @Embedded(prefix = "potential_savings_")
    var potentialSavings: Money?,
    @ColumnInfo(name = "available_for_saving")
    var availableForSaving: Int

) {
    companion object {
        /**
         * convert a POJO with info from the server in encrypted info to store in the db
         * @param accountUid account uid to build the relationship between tables
         * @param feed plain text info from the server
         * @return encrypted info ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountFeedUnencrypted(
            accountUid: String,
            feed: Feed,
            availableForSavingValue: Int
        ): FeedsEntity =
            FeedsEntity(
                accountUid = accountUid,
                feedItemUid = feed.feedItemUid,
                categoryUid = feed.categoryUid,
                amount = feed.amount,
                sourceAmount = feed.sourceAmount,
                direction = feed.direction,
                updatedAt = feed.updatedAt,
                transactionTime = feed.transactionTime,
                settlementTime = feed.settlementTime,
                source = feed.source,
                status = feed.status,
                counterPartyType = feed.counterPartyType,
                counterPartyUid = feed.counterPartyUid,
                counterPartyName = feed.counterPartyName,
                counterPartySubEntityUid = feed.counterPartySubEntityUid,
                counterPartySubEntityName = feed.counterPartySubEntityName,
                counterPartySubEntityIdentifier = feed.counterPartySubEntityIdentifier,
                counterPartySubEntitySubIdentifier = feed.counterPartySubEntitySubIdentifier,
                reference = feed.reference,
                country = feed.country,
                spendingCategory = feed.spendingCategory,
                potentialSavings = if (feed.direction == Direction.OUT.value) feed.amount?.getPotentialSavings() else null,
                availableForSaving = availableForSavingValue
            )

    }

    /**
     * decrypt info from the db and return it as a POJO
     * @return POJO with plain text
     */
    fun toAccountFeedUnencrypted(): Feed =
        Feed(
            feedItemUid = this.feedItemUid,
            categoryUid = this.categoryUid,
            amount = this.amount,
            sourceAmount = this.sourceAmount,
            direction = this.direction,
            updatedAt = this.updatedAt,
            transactionTime = this.transactionTime,
            settlementTime = this.settlementTime,
            source = this.source,
            status = this.status,
            counterPartyType = this.counterPartyType,
            counterPartyUid = this.counterPartyUid,
            counterPartyName = this.counterPartyName,
            counterPartySubEntityUid = this.counterPartySubEntityUid,
            counterPartySubEntityName = this.counterPartySubEntityName,
            counterPartySubEntityIdentifier = this.counterPartySubEntityIdentifier,
            counterPartySubEntitySubIdentifier = this.counterPartySubEntitySubIdentifier,
            reference = this.reference,
            country = this.country,
            spendingCategory = this.spendingCategory,
            potentialSavings = this.potentialSavings,
            availableForSaving = SavingState.fromInt(this.availableForSaving)
        )


}

