package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.room.Dao
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity


@Dao
abstract class BalanceDao : BaseDao<BalanceEntity>()