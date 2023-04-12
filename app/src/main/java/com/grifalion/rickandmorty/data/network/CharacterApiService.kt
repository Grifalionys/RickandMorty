package com.grifalion.rickandmorty.data.network

import com.grifalion.rickandmorty.data.models.Character
import com.grifalion.rickandmorty.data.models.CharacterList
import com.grifalion.rickandmorty.data.models.PagedResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<PagedResponse<Character>>

    @GET("character")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterList>

    @GET("character")
    suspend fun getCharactersByStatusAndGender(@Query("status") status: String,
                                              @Query("gender") gender: String,
                                              @Query("page") page: Int): Response<CharacterList>

    @GET("character")
    suspend fun getCharactersByStatus(@Query("status") status: String,
                                     @Query("page") page: Int): Response<CharacterList>

    @GET("character")
    suspend fun getCharactersByGender(@Query("gender")gender: String,
                                      @Query("page") page: Int): Response<CharacterList>

    companion object{
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        }

        val api: CharacterApiService by lazy {
        retrofit.create(CharacterApiService::class.java)
        }
    }
}