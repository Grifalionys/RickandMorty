package com.grifalion.rickandmorty.api

import com.grifalion.rickandmorty.models.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharacterList>

}