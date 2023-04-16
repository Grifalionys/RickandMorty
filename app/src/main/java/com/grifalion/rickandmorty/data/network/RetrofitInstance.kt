package com.grifalion.rickandmorty.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    var characterApi: CharacterApiService? = null
    suspend fun getInstance(): CharacterApiService {
        if (characterApi == null) {
            val loginInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loginInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            characterApi = retrofit.create(CharacterApiService::class.java)
        }
        return characterApi!!
    }
}