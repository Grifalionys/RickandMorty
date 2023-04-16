package com.grifalion.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.grifalion.rickandmorty.data.network.models.Character
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import kotlinx.coroutines.flow.Flow

class Repository {

    fun getCharacters(name: String, status: String, gender: String): LiveData<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 42,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CharacterDataSource(name, status, gender)
            },
            initialKey = 1
        ).liveData
    }

    fun getCharacterss(name: String, status: String, gender: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 42,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CharacterDataSource(name, status, gender)
            },
            initialKey = 1
        ).flow
    }
}