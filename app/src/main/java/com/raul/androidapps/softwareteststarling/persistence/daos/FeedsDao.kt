package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity


@Dao
abstract class FeedsDao : BaseDao<FeedsEntity>(){
    @Query("SELECT * FROM feeds WHERE feed_item_uid = :feedId")
    abstract fun getFeed(feedId: String): FeedsEntity
}