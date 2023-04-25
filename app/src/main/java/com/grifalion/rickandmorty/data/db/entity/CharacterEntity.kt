package com.grifalion.rickandmorty.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "Image")
    val image: String,
    @ColumnInfo(name = "Species")
    val species: String,
    @ColumnInfo(name = "Status")
    val status: String,
    @ColumnInfo(name = "Type")
    val type: String,
    val created: String,
    @ColumnInfo(name = "Gender")
    val gender: String,
    val locationName: String,
    val locationUrl: String,
    val originName: String,
    val originUrl: String,
    val episode: List<String>
)
