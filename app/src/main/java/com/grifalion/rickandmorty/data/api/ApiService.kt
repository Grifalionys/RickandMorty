package com.grifalion.rickandmorty.data.api

import io.reactivex.Observable
import com.grifalion.rickandmorty.data.repsonse.character.CharacterResponse
import com.grifalion.rickandmorty.data.repsonse.episode.EpisodeResponse
import com.grifalion.rickandmorty.data.repsonse.location.LocationResponse
import com.grifalion.rickandmorty.domain.models.episode.Episode


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {



    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int,
                              @Query("name") name: String,
                              @Query("status") status: String,
                              @Query("gender") gender: String,
                              @Query("species") species: String

    ): Response<CharacterResponse>

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int,
                              @Query("name") name: String,
                              @Query("type") type: String,
                              @Query("dimension") dimension: String,

    ): Response<LocationResponse>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): Response<EpisodeResponse>

    @GET("episode/{id}")
    fun getDetailEpisode(@Path("id") id: String): Observable<List<Episode>>

    @GET("character/{id}")
    fun getDetailCharacter(@Path("id") id: String): Observable<List<com.grifalion.rickandmorty.domain.models.character.Character>>

    @GET("location/")
    fun getDetailLocation(@Query("name") name: String): Observable<LocationResponse>

}