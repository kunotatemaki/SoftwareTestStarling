package com.raul.androidapps.softwaretesttemplate.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "foo", indices = [(Index(value = arrayOf("name"), unique = true))])
data class FooEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    var name: String
)

