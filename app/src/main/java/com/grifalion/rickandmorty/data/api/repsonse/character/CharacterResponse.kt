package com.grifalion.rickandmorty.data.api.repsonse.character

import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.domain.models.character.CharacterResult

data class CharacterResponse (
    val info: CharacterInfoResponse?,
    var results: List<CharacterResultResponse>
    )