package com.grifalion.rickandmorty.data.db.entity.character


data class CharacterDb(
    var info: CharacterInfoDb,
    var results: List<CharacterDbModel>
)
