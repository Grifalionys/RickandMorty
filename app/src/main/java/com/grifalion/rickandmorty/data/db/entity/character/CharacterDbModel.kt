package com.grifalion.rickandmorty.data.db.entity.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_character")
data class CharacterDbModel (
    var created: String,
    var gender: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String,
    var name: String,
    var species: String,
    var status: String,
    var type: String,
    var url: String
    )