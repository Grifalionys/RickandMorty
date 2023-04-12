package com.grifalion.rickandmorty.repository

import com.grifalion.rickandmorty.data.models.Character
import com.grifalion.rickandmorty.data.network.RetrofitInstance
import com.grifalion.rickandmorty.data.models.CharacterList
import com.grifalion.rickandmorty.data.models.PagedResponse
import retrofit2.Response

class Repository {
    suspend fun getCharacter(page: Int): Response<PagedResponse<Character>> {
        return RetrofitInstance.api.getCharacters(page)
    }
    suspend fun getCharactersByName(name: String): Response<CharacterList>{
        return RetrofitInstance.api.getCharactersByName(name)
    }

    suspend fun getCharactersByStatusAndGender(status: String, gender: String, page: Int): Response<CharacterList>{
        return RetrofitInstance.api.getCharactersByStatusAndGender(status,gender,page)
    }

    suspend fun getCharactersByStatus(status: String,page: Int): Response<CharacterList>{
        return RetrofitInstance.api.getCharactersByStatus(status,page)
    }

    suspend fun getCharactersByGender(gender: String, page: Int): Response<CharacterList>{
        return RetrofitInstance.api.getCharactersByGender(gender,page)
    }
}