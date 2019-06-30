package com.raul.androidapps.softwareteststarling.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.softwareteststarling.BuildConfig
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.security.Encryption


@Entity(
    tableName = "identifiers",
    indices = [(Index(value = arrayOf("account_uid"), unique = true))]
)
data class IdentifiersEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "account_uid")
    val accountUid: String,
    @ColumnInfo(name = "account_identifier")
    val accountIdentifier: String?,
    @ColumnInfo(name = "bank_identifier")
    var bankIdentifier: String?,
    @ColumnInfo(name = "iban")
    var iban: String?,
    @ColumnInfo(name = "bic")
    var bic: String?
) {
    companion object {
        /**
         * convert a POJO with info from the server in encrypted info to store in the db
         * @param accountUid account uid to build the relationship between tables
         * @param identifiersResponse plain text info from the server
         * @param encryption class for encrypt/decrypt
         * @return encrypted info ready to be stored in the db
         */
        @JvmStatic
        fun fromAccountIdentifierUnencrypted(
            accountUid: String,
            identifiersResponse: IdentifiersResponse?,
            encryption: Encryption
        ): IdentifiersEntity =
            IdentifiersEntity(
                accountUid = accountUid,
                accountIdentifier = encryption.encryptString(
                    identifiersResponse?.accountIdentifier,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                bankIdentifier = encryption.encryptString(
                    identifiersResponse?.bankIdentifier,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                iban = encryption.encryptString(
                    identifiersResponse?.iban,
                    BuildConfig.ENCRYPTION_ALIAS
                ),
                bic = encryption.encryptString(
                    identifiersResponse?.bic,
                    BuildConfig.ENCRYPTION_ALIAS
                )
            )
    }

    /**
     * decrypt info from the db and return it as a POJO
     * @param encryption class for encrypt/decrypt
     * @return POJO with plain text
     */
    fun toAccountIdentifierUnencrypted(encryption: Encryption): IdentifiersResponse =
        IdentifiersResponse(
            accountIdentifier = this.accountIdentifier,
            bankIdentifier = encryption.decryptString(
                this.bankIdentifier,
                BuildConfig.ENCRYPTION_ALIAS
            ),
            iban = encryption.decryptString(this.iban, BuildConfig.ENCRYPTION_ALIAS),
            bic = encryption.decryptString(this.bic, BuildConfig.ENCRYPTION_ALIAS)
        )
}

