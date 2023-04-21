package com.grifalion.rickandmorty.data.repsonse.character

import com.grifalion.rickandmorty.domain.models.character.Character

data class CharacterList (
    val info: Info,
    var results: ArrayList<Character>
        ) 