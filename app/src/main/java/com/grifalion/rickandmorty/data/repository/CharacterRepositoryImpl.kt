package com.grifalion.rickandmorty.data.repository

import com.grifalion.rickandmorty.data.api.CharacterApiService
import com.grifalion.rickandmorty.data.db.dao.CharacterDao
import com.grifalion.rickandmorty.data.db.entity.character.CharacterDbModel
import com.grifalion.rickandmorty.data.repsonse.character.CharacterResponse
import com.grifalion.rickandmorty.domain.models.character.Character
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
  //  private val characterDao: CharacterDao

    ): CharacterRepository {


    override suspend fun getCharacter(
        page: Int,
        name: String,
        gender: String,
        status: String,
        species: String): CharacterResponse {
        val api = apiService.getCharacter(page,name,gender,status,species)
        //characterDao.insertCharacter(CharacterDbModel())
        return api

    }

    override suspend fun insertCharacter(list: List<Character>) {

    }


}