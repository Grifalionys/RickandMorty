package com.grifalion.rickandmorty.domain.models.character
import com.grifalion.rickandmorty.domain.models.location.Location

data class Character (
    var id : Int,
    var name: String,
    var status : String,
    var species: String,
    var gender: String,
    var origin : Location,
    var location : CharacterLocation,
    var image : String,
    var created: String,
    var type: String,
    var episode : List<String>
)