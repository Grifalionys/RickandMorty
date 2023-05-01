package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel
import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterResponse
import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.domain.models.character.CharacterInfo
import com.grifalion.rickandmorty.domain.models.character.CharacterModel
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val characterDao: CharacterDao
    ): CharacterRepository {


   /* fun mapCharacterForNetwork(characterResponse: CharacterResponse) = CharacterModel(
        info = characterResponse.info(CharacterInfo(
            pages = 0,
            next = "",
            count = 0,
            prev = ""
        )),
        result = characterResponse.results
    )*/

    fun mapCharacterForDb(character: com.grifalion.rickandmorty.domain.models.character.Character): CharacterDbModel{
        return CharacterDbModel(
            id = character.id,
            name = character.name,
            image = character.image,
            gender = character.gender,
            status = character.status,
            url = character.status,
            created = "",
            type = "",
            species = character.species
        )
    }
    private fun listMapCharacterDb(list: List<com.grifalion.rickandmorty.domain.models.character.Character>) = list.map {
        mapCharacterForDb(it)
    }

    override suspend fun getCharacter(
        page: Int,
        name: String,
        gender: String,
        status: String,
        species: String): CharacterResponse {
        val characterApi = apiService.getCharacter(page,name,gender,status,species)
        // val list =
        // characterDao.insertCharacter(listMapCharacterDb(list))
        return characterApi

    }


    override suspend fun insertCharacter(list: List<Character>) {
        characterDao.insertCharacter(listMapCharacterDb(list))
    }


}