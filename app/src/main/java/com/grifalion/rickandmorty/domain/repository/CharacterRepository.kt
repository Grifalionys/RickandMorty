package com.grifalion.rickandmorty.domain.repository

import com.grifalion.rickandmorty.domain.models.character.CharacterModel
import com.grifalion.rickandmorty.domain.models.character.CharacterResult

interface CharacterRepository {

    suspend fun getCharacter(page: Int, name: String, status: String, gender: String, species: String): CharacterModel

    suspend fun insertCharacter(list: List<CharacterResult>)

    suspend fun getListCharacters(): List<CharacterResult>
}