package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.room.Dao
import com.raul.androidapps.softwareteststarling.persistence.entities.FeedsEntity


@Dao
abstract class FeedsDao : BaseDao<FeedsEntity>()