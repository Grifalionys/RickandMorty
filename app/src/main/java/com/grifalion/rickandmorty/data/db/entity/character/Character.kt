package com.grifalion.rickandmorty.data.db.entity.character

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Character(
    var info: CharacterInfo,
    var results: List<CharacterDbModel>
)
