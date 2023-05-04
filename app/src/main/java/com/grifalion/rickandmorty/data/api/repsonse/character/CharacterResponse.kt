package com.grifalion.rickandmorty.data.api.repsonse.character


data class CharacterResponse (
    val info: CharacterInfoResponse?,
    var results: List<CharacterResultResponse>
    )