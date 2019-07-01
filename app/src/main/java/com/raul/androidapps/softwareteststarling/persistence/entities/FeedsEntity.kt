package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.*
import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.model.Money
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
    @Embedded(prefix = "amount")
    var amount: Money?,
    @Embedded(prefix = "source_amount")
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
    var spendingCategory: String?
) {
    companion object {
        /**
         * convert a POJO with info from the server in encrypted info to store in the db
         * @param accountUid account uid to build the relationship between tables
         * @param feed plain text info from the server
         * @param encryption class for encrypt/decrypt
         * @return encrypted info ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountFeedUnencrypted(
            accountUid: String,
            feed: Feed,
            encryption: Encryption
        ): FeedsEntity =
            FeedsEntity(
                accountUid = accountUid,
                feedItemUid = encryption.encryptString(
                    feed.feedItemUid,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                categoryUid = encryption.encryptString(
                    feed.categoryUid,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                amount = feed.amount,
                sourceAmount = feed.sourceAmount,
                direction = encryption.encryptString(
                    feed.direction,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                updatedAt = feed.updatedAt,
                transactionTime = feed.transactionTime,
                settlementTime = feed.settlementTime,
                source = encryption.encryptString(
                    feed.source,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                status = encryption.encryptString(
                    feed.status,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartyType = encryption.encryptString(
                    feed.counterPartyType,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartyUid = encryption.encryptString(
                    feed.counterPartyUid,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartyName = encryption.encryptString(
                    feed.counterPartyName,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartySubEntityUid = encryption.encryptString(
                    feed.counterPartySubEntityUid,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartySubEntityName = encryption.encryptString(
                    feed.counterPartySubEntityName,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartySubEntityIdentifier = encryption.encryptString(
                    feed.counterPartySubEntityIdentifier,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                counterPartySubEntitySubIdentifier = encryption.encryptString(
                    feed.counterPartySubEntitySubIdentifier,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                reference = encryption.encryptString(
                    feed.reference,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                country = encryption.encryptString(
                    feed.country,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                spendingCategory = encryption.encryptString(
                    feed.spendingCategory,
                    BuildConfig.ENCRYPTION_ALIAS
                )
            )

    }

    /**
     * decrypt info from the db and return it as a POJO
     * @param encryption class for encrypt/decrypt
     * @return POJO with plain text
     */
    fun toAccountFeedUnencrypted(encryption: Encryption): Feed =
        Feed(
            feedItemUid = encryption.decryptString(
                this.feedItemUid,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            categoryUid = encryption.decryptString(
                this.categoryUid,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            amount = this.amount,
            sourceAmount = this.sourceAmount,
            direction = encryption.decryptString(
                this.direction,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            updatedAt = this.updatedAt,
            transactionTime = this.transactionTime,
            settlementTime = this.settlementTime,
            source = encryption.decryptString(
                this.source,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            status = encryption.decryptString(
                this.status,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartyType = encryption.decryptString(
                this.counterPartyType,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartyUid = encryption.decryptString(
                this.counterPartyUid,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartyName = encryption.decryptString(this.counterPartyName, BuildConfig.ENCRYPTION_ALIAS),
            counterPartySubEntityUid = encryption.decryptString(
                this.counterPartySubEntityUid,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartySubEntityName = encryption.decryptString(
                this.counterPartySubEntityName,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartySubEntityIdentifier = encryption.decryptString(
                this.counterPartySubEntityIdentifier,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            counterPartySubEntitySubIdentifier = encryption.decryptString(
                this.counterPartySubEntitySubIdentifier,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            reference = encryption.decryptString(this.reference, BuildConfig.ENCRYPTION_ALIAS),
            country = encryption.decryptString(this.country, BuildConfig.ENCRYPTION_ALIAS),
            spendingCategory = encryption.decryptString(this.spendingCategory, BuildConfig.ENCRYPTION_ALIAS)
        )


}

