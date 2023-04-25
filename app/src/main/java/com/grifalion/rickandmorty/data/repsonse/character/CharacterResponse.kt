package com.grifalion.rickandmorty.data.repsonse.character

import com.grifalion.rickandmorty.domain.models.character.Character

data class CharacterResponse (
    val info: Info,
    var results: List<Character>
    )