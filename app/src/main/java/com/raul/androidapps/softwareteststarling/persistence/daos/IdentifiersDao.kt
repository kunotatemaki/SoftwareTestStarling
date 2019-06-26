package com.raul.androidapps.softwareteststarling.persistence.daos

import androidx.room.Dao
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity


@Dao
abstract class IdentifiersDao : BaseDao<IdentifiersEntity>()