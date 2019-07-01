package com.raul.androidapps.softwareteststarling.persistence.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity


class AccountWithAllInfo {
    @Embedded
    lateinit var account: AccountEntity

    @Relation(parentColumn = "account_uid", entityColumn = "account_uid")
    var identifiers: List<IdentifiersEntity> = mutableListOf()

    @Relation(parentColumn = "account_uid", entityColumn = "account_uid")
    var balance: List<BalanceEntity> = mutableListOf()

    @Relation(parentColumn = "account_uid", entityColumn = "account_uid")
    var feeds: List<FeedsEntity> = mutableListOf()
}