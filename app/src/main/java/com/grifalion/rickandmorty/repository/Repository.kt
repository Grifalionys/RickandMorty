package com.grifalion.rickandmorty.repository

import com.grifalion.rickandmorty.api.RetrofitInstance
import com.grifalion.rickandmorty.models.CharacterList
import retrofit2.Response

class Repository {
    suspend fun getCharacter(page: Int): Response<CharacterList> {
        return RetrofitInstance.api.getCharacters(page)
    }
}