package com.grifalion.rickandmorty.data.api.repsonse.character

data class CharacterInfoResponse(
    val count: Int?,
    val next: String?,
    val pages: Int?,
    val prev: String?
)
