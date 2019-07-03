package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity


@Dao
abstract class FeedsDao : BaseDao<FeedsEntity>() {
    @Query("SELECT * FROM feeds WHERE feed_item_uid = :feedId")
    abstract fun getFeed(feedId: String): FeedsEntity

    @Query("SELECT * FROM feeds WHERE account_uid = :accountUid AND direction LIKE :direction AND available_for_saving = :state")
    abstract fun getPotentialSavings(accountUid: String, direction: String, state: Int): LiveData<List<FeedsEntity>>

    @Query("UPDATE feeds SET available_for_saving = :state WHERE feed_item_uid IN (:ids)")
    abstract fun markFeedsAsSaved(ids: List<String>, state: Int)
}