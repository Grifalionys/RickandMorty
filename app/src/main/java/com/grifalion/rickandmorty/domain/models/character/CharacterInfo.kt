package com.grifalion.rickandmorty.domain.models.character

data class CharacterInfo(
    var count: Int?,
    var next: String?,
    var pages: Int?,
    var prev: String?
)