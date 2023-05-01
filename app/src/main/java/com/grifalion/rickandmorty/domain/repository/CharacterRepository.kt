package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterResponse

interface CharacterRepository {

    suspend fun getCharacter(page: Int, name: String, status: String, gender: String, species: String): CharacterResponse

    suspend fun insertCharacter(list: List<com.grifalion.rickandmorty.domain.models.character.Character>)
}