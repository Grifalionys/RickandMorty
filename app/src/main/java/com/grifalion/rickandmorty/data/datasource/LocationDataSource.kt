package com.grifalion.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grifalion.rickandmorty.data.api.RetrofitInstance
import com.grifalion.rickandmorty.domain.models.location.Location

class LocationDataSource(private val name: String, private val type: String, private val dimension: String): PagingSource<Int, Location>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = RetrofitInstance.getInstance()
            val responseData = mutableListOf<Location>()
            responseData.addAll(response.getLocations(currentLoadingPageKey, name,type,dimension).body()!!.results)

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
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}