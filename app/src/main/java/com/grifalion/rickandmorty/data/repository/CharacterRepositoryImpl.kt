package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.mappers.CharacterMapper
import com.grifalion.rickandmorty.domain.models.character.CharacterDetail
import com.grifalion.rickandmorty.domain.models.character.CharacterModel
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val characterDao: CharacterDao,
    private val mapper: CharacterMapper
    ): CharacterRepository {

    override suspend fun getCharacter(
        page: Int,
        name: String,
        status: String,
        gender: String,
        species: String): CharacterModel {
        val characterApi = apiService.getCharacter(page,name,gender,status,species)
        val listCharacters = mapper.mapCharacterResponseForCharacter(characterApi)
        characterDao.insertCharacter(mapper.mapListResultResponseForListDb(listCharacters.result))
        return listCharacters
    }

    override suspend fun insertCharacter(list: List<CharacterResult>) {
        characterDao.insertCharacter(mapper.mapListResultResponseForListDb(list))
    }

    override suspend fun getListCharacters(): List<CharacterResult> {
        var listCharacters = emptyList<CharacterResult>()
        CoroutineScope(Dispatchers.IO).launch {
            listCharacters = (characterDao.getAllCharacters()).map {
                mapper.mapCharacterResultDbForCharacterResult(it)
            }
        }
        return listCharacters
    }



}