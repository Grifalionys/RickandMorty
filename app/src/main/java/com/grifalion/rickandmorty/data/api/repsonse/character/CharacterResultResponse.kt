package com.grifalion.rickandmorty.data.api.repsonse.character

data class CharacterResultResponse(
    var created: String?,
    var episode: List<String>?,
    var gender: String?,
    var id: Int?,
    var image: String?,
    var location: CharacterLocationResponse,
    var name: String?,
    var species: String?,
    var status: String?,
    var type: String?,
    var url: String?
)
