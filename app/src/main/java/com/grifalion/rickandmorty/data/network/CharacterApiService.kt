package com.grifalion.rickandmorty.data.network

import com.grifalion.rickandmorty.data.network.models.Character
import com.grifalion.rickandmorty.data.network.models.CharacterList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int,
                              @Query("name") name: String,
                              @Query("status") status: String,
                              @Query("gender") gender: String,
                              @Query("species") species: String

    ): Response<CharacterList>

}