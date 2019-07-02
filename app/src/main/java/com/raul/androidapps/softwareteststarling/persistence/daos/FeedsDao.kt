package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity


@Dao
abstract class FeedsDao : BaseDao<FeedsEntity>(){
    @Query("SELECT * FROM feeds WHERE feed_item_uid = :feedId")
    abstract fun getFeed(feedId: String): FeedsEntity

    @Query("SELECT * FROM feeds WHERE account_uid = :accountUid AND direction LIKE :direction AND sent_to_goal = 0")
    abstract fun getPotentialSavings(accountUid: String, direction: String): LiveData<List<FeedsEntity>>

    @Query("UPDATE feeds SET sent_to_goal = 1 WHERE feed_item_uid IN (:ids)")
    abstract fun markFeedsAsSaved(ids: List<String>)
}