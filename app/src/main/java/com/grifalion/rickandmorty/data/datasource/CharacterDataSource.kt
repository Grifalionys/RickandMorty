package com.grifalion.rickandmorty.data.datasource

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val repository: CharacterRepository,
    private val application: Application,
    private val name: String,
    private val status: String,
    private val gender: String,
    private val species: String,
    ): PagingSource<Int, CharacterResult>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
       return try {
            val page = params.key ?: 1
            val responseData = arrayListOf<CharacterResult>()
            if(hasConnected(application.applicationContext)) {
                responseData.addAll((repository.getCharacter(page,name,gender,status,species)).result)
            } else {
                val listCharacters = repository.getListCharacters()
                responseData.addAll(listCharacters)
            }

            val prevKey = if(page == 1) null else page - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = page.plus(1)
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
        return null
        }
    }

    private fun hasConnected(context: Context): Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.activeNetworkInfo
        return network != null && network.isConnected
    }

