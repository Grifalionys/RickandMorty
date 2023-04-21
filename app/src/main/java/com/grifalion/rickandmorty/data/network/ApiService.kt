package com.grifalion.rickandmorty.data.network

import com.grifalion.rickandmorty.data.repsonse.character.CharacterList
import com.grifalion.rickandmorty.data.repsonse.episode.EpisodeList
import com.grifalion.rickandmorty.data.repsonse.location.LocationList

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int,
                              @Query("id") id: Int,
                              @Query("name") name: String,
                              @Query("status") status: String,
                              @Query("gender") gender: String,
                              @Query("species") species: String

    ): Response<CharacterList>

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int,
                              @Query("name") name: String,
                              @Query("type") type: String,
                              @Query("dimension") dimension: String,

    ): Response<LocationList>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): Response<EpisodeList>


}