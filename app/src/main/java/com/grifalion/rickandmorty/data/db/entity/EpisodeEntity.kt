package com.grifalion.rickandmorty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "episodes_table")
data class EpisodeEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<String>?
)