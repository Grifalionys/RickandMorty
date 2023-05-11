package com.grifalion.rickandmorty.data.api

import com.grifalion.rickandmorty.data.api.repsonse.character.CharacterResponse
import com.grifalion.rickandmorty.data.api.repsonse.location.LocationResponse
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.models.episode.EpisodeResult
import com.grifalion.rickandmorty.domain.models.location.Location
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {
    @GET("character/")
    suspend fun getCharacter(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
        @Query("species") species: String
    ): CharacterResponse
    @GET("episode/{id}")
    fun getListEpisodesByIdIntoCharacterDetail(@Path("id") id: String): Observable<List<EpisodeResult>>

    @GET("location/")
    fun getDetailLocation(@Query("name") name: String): Observable<Location>

    companion object {
            private const val BASE_URL = "https://rickandmortyapi.com/api/"
            var characterRetrofit: CharacterApiService? = null
            fun getInstance(): CharacterApiService {
                if(characterRetrofit == null) {
                    val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                        .build()
                    val retrofit = Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    characterRetrofit = retrofit.create(CharacterApiService::class.java)
                }
                return characterRetrofit!!
            }
        }
    }
