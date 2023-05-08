package com.grifalion.rickandmorty.data.db.entity.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_location")
data class LocationDbModel(
    val created: String,
    val dimension: String,
    @PrimaryKey()
    val id: Int,
    val name: String,
    val type: String,
    val url: String
)
