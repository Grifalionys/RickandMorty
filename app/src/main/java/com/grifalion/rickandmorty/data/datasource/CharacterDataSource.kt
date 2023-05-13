package com.grifalion.rickandmorty.data.datasource

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.repository.CharacterRepository
import retrofit2.HttpException
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
            var nextKey : Int? = 0
            val responseData = if (hasConnected(application.applicationContext)) {
                val response = repository.getCharacter(page,name,gender,status,species)
                nextKey = if(response.info.next == null) null else page+1
                response.result
            } else {
                val listCharacters = repository.getListCharacters((page-1) * params.loadSize, params.loadSize,name,gender,status,species)
                nextKey = if(listCharacters.isNotEmpty()) page+1 else null
                listCharacters
            }

           val prevKey = if(page == 1) null else page - 1

           return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
            } catch (e: Exception){
            return LoadResult.Error(e)
            } catch (e: HttpException) {
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
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

