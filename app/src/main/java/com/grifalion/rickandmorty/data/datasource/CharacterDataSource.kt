package com.grifalion.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grifalion.rickandmorty.data.network.RetrofitInstance
import com.grifalion.rickandmorty.data.network.models.Character

class CharacterDataSource(private val name: String,private val status: String, private val gender: String,private val species: String): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = RetrofitInstance.getInstance()
            val responseData = mutableListOf<Character>()
            responseData.addAll(response.getCharacters(currentLoadingPageKey, name, status, gender,species).body()!!.results)

            val prevKey = if(currentLoadingPageKey == 1){
                null
            } else {
                currentLoadingPageKey - 1
            }
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}